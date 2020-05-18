package br.com.onebr.service;

import br.com.onebr.controller.response.ImageRes;
import br.com.onebr.exception.ConflictApiException;
import br.com.onebr.model.config.Image;
import br.com.onebr.repository.ImageRepository;
import br.com.onebr.service.util.S3FileManager;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import javax.activation.MimetypesFileTypeMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@Slf4j
public class ImageService {

    @Value("${aws.s3.bucket.images}")
    private String bucketImagesName;

    @Value("${local.img.path}")
    private String localImagesPath;

    @Value("${app.img.path}")
    private String appImagePath;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private S3FileManager s3FileManager;

    public ImageRes saveImage(MultipartFile multipartFile) {
        final String fileName = multipartFile.getOriginalFilename();
        log.info("message=Uploading image. fileName={}", fileName);

        Image image = imageRepository.findOneByName(fileName);
        if (image != null) {
            log.error("message=Attempt to upload image that already exists on database. fileName={}", fileName);
            throw new ConflictApiException("image.already.exists");
        }

        image = imageRepository.save(Image.builder()
            .name(fileName)
            .build());

        final File file = new File(localImagesPath + fileName);
        final MimetypesFileTypeMap mimeTypesMap = new MimetypesFileTypeMap();
        final String mimeType = mimeTypesMap.getContentType(file);

        try (OutputStream os = Files.newOutputStream(file.toPath())) {
            os.write(multipartFile.getBytes());
            s3FileManager.upload(bucketImagesName, file, mimeType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ImageRes.builder()
            .id(image.getId())
            .name(image.getName())
            .path(appImagePath)
            .build();
    }
}
