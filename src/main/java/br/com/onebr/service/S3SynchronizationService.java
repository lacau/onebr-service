package br.com.onebr.service;

import br.com.onebr.model.config.Image;
import br.com.onebr.repository.ImageRepository;
import br.com.onebr.service.util.S3FileManager;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
@Slf4j
public class S3SynchronizationService {

    @Value("${aws.s3.bucket.images}")
    private String bucketImagesName;

    @Value("${local.img.path}")
    private String localImagesPath;

    @Autowired
    private AmazonS3 amazonS3;

    @Autowired
    private S3FileManager s3FileManager;

    @Autowired
    private ImageRepository imageRepository;

    @Async
    public void synchronizeImages() {
        log.info("message=Start synchronizing dynamic images between S3 and database. bucketName={}", bucketImagesName);
        final List<Image> images = imageRepository.findAll();
        if (CollectionUtils.isEmpty(images)) {
            log.info("message=Nothing on database. Synchronizing skipped. bucketName={}", bucketImagesName);
            return;
        }

        log.info("message=Start synchronizing dynamic images between S3 and local file system. bucketName={}", bucketImagesName);

        List<S3ObjectSummary> summaries = new ArrayList<>();
        ListObjectsV2Result result;

        log.info("message=Obtain images names from S3 bucket. bucketName={}", bucketImagesName);
        try {
            ListObjectsV2Request req = new ListObjectsV2Request().withBucketName(bucketImagesName);
            do {
                result = amazonS3.listObjectsV2(req);
                summaries.addAll(result.getObjectSummaries());
            } while (result.isTruncated());
        } catch (AmazonServiceException e) {
            log.error("message=Call was transmitted successfully, but Amazon S3 couldn't process it. bucketName={}", bucketImagesName);
            e.printStackTrace();
        } catch (SdkClientException e) {
            log.error(
                "message=Amazon S3 couldn't be contacted for a response, or the client couldn't parse the response. bucketName={}", bucketImagesName);
            e.printStackTrace();
        }

        log.info("message=Cleaning images on S3. bucket={}", bucketImagesName);
        final List<String> imageNames = images.stream().map(Image::getName).collect(Collectors.toList());
        for (Iterator<S3ObjectSummary> it = summaries.iterator(); it.hasNext(); ) {
            S3ObjectSummary next = it.next();
            if (!imageNames.contains(next.getKey())) {
                s3FileManager.delete(bucketImagesName, next.getKey());
                it.remove();
            }
        }

        log.info("message=Obtain images names from local file system. path={}", localImagesPath);
        final Set<String> localFilesNames = Stream.of(new File(localImagesPath).listFiles())
            .filter(file -> !file.isDirectory())
            .map(File::getName)
            .collect(Collectors.toSet());

        int count = 0;
        if (summaries.size() != localFilesNames.size()) {
            log.warn("message=S3 and local file system is out of sync. bucketName={}, path={}", bucketImagesName, localImagesPath);
            count = synchronizeS3AndLocalFileSystem(summaries, localFilesNames);
        }

        log.info("message=Ending synchronizing dynamic images between S3 and local file system. bucketName={}, synchronizedFiles={}",
            bucketImagesName, count);
    }

    private int synchronizeS3AndLocalFileSystem(List<S3ObjectSummary> summaries, Set<String> localFilesNames) {
        int count = 0;
        for (S3ObjectSummary summary : summaries) {
            if (!localFilesNames.contains(summary.getKey())) {
                log.info("message=Synchronizing local file. fileName={}", summary.getKey());
                final File downloaded = s3FileManager.downloadAndPersist(bucketImagesName, summary.getKey(), localImagesPath);
                if (downloaded == null) {
                    log.error("message=Skipping... S3 file is missing. bucketName={}, fileName={}", bucketImagesName, summary.getKey());
                    continue;
                }

                count++;
                log.info("message=S3 file successful synchronized. bucketName={}, fileName={}, path={}", bucketImagesName, downloaded.getName(),
                    localImagesPath);
            }
        }

        return count;
    }
}
