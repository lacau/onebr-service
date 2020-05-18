package br.com.onebr.service.util;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class S3FileManager {

    private static final int BUFFER_SIZE = 1024;

    @Autowired
    private AmazonS3 amazonS3;

    @Async
    public void upload(String bucketName, File file, String mediaType) {
        log.info("message=Uploading file to S3. bucket={}, fileName={}, mediaType={}", bucketName, file.getName(), mediaType);
        final PutObjectRequest request = new PutObjectRequest(bucketName, file.getName(), file);
        final ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(mediaType);
        request.setMetadata(metadata);

        amazonS3.putObject(request);
        log.info("message=File uploaded to S3. bucket={}, fileName={}, mediaType={}", bucketName, file.getName(), mediaType);
    }

    public void delete(String bucketName, String key) {
        final DeleteObjectRequest request = new DeleteObjectRequest(bucketName, key);

        try {
            amazonS3.deleteObject(request);
            log.info("message=S3 file deleted. bucket={}, file={}", bucketName, key);
        } catch (SdkClientException e) {
            log.error("message=Error on deleting file form S3. bucket={}, file={}, error={}", bucketName, key, e.getMessage());
        }
    }

    public File downloadAndPersist(String bucketName, String fileName, String filePath) {
        final S3Object o = amazonS3.getObject(bucketName, fileName);

        try {
            final File file = new File(filePath + o.getKey());
            final S3ObjectInputStream s3is = o.getObjectContent();
            final FileOutputStream fos = new FileOutputStream(file);
            final byte[] buffer = new byte[BUFFER_SIZE];

            int readLength;
            while ((readLength = s3is.read(buffer)) > 0) {
                fos.write(buffer, 0, readLength);
            }

            s3is.close();
            fos.close();

            return file;
        } catch (AmazonServiceException e) {
            log.error("message=Error on S3 file download. bucket={}, fileName={}, errorMessage={}", bucketName, fileName, e.getErrorMessage());
        } catch (FileNotFoundException e) {
            log.error("message=File not found on S3 bucket. bucket={}, fileName={}, message={}", bucketName, fileName, e.getMessage());
        } catch (IOException e) {
            log.error("message=Error on read/write downloaded file from S3. bucket={}, fileName={}, errorMessage={}", bucketName, fileName,
                e.getMessage());
        }

        return null;
    }
}
