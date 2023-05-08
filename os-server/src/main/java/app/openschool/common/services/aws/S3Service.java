package app.openschool.common.services.aws;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@ConditionalOnMissingBean(value = FileStorageLocalService.class)
public class S3Service implements FileStorageService {

  private final String bucketName;

  private static final Logger logger = LoggerFactory.getLogger("S3Service");

  private final AmazonS3 amazonS3;

  private String filename;

  public S3Service(@Value("${application.bucket.name}") String bucketName, AmazonS3 amazonS3) {
    this.bucketName = bucketName;
    this.amazonS3 = amazonS3;
  }

  @Override
  public String uploadFile(MultipartFile file) {

    filename = generateUniqueFilename(file.getOriginalFilename());
    File convertedFile = convertMultiPartFileToFile(file);
    //after write
    Path filePath = convertedFile.toPath();
    amazonS3.putObject(
        new PutObjectRequest(bucketName, filename, convertedFile)
            .withCannedAcl(CannedAccessControlList.PublicRead));

    try {
      Files.delete(filePath);

    } catch (IOException e) {
      logger.error("Error while deleting temporary file");
    }
    //     boolean deletedFile = convertedFile.delete();

    return filename;
  }

  @Override
  public void deleteFile(String fileName) {
    amazonS3.deleteObject(bucketName, fileName);
  }

  private String generateUniqueFilename(String originalFileName) {
    String fileExtension = FilenameUtils.getExtension(originalFileName);
    return UUID.randomUUID() + "." + fileExtension;
  }

  private File convertMultiPartFileToFile(MultipartFile file) {
    File convertedFile = new File(filename);
    try (FileOutputStream out = new FileOutputStream(convertedFile)) {
      out.write(file.getBytes());
    } catch (IOException e) {
      logger.error("Error converting multipartFile to file");
    }
    return convertedFile;
  }
}
