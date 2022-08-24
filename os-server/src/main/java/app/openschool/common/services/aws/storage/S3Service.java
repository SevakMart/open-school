package app.openschool.common.services.aws.storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Profile({"!local", "!test"})
@Service
public class S3Service implements FileStorageService {

  private final String bucketName;

  private final Logger logger = LoggerFactory.getLogger("S3Service");

  private final AmazonS3 amazonS3;

  public S3Service(@Value("${application.bucket.name}") String bucketName, AmazonS3 amazonS3) {
    this.bucketName = bucketName;
    this.amazonS3 = amazonS3;
  }

  @Override
  public String uploadFile(MultipartFile file) {
    String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
    File convertedFile = convertMultiPartFileToFile(file);
    amazonS3.putObject(
        new PutObjectRequest(bucketName, fileName, convertedFile)
            .withCannedAcl(CannedAccessControlList.PublicRead));
    boolean deletedFile = convertedFile.delete();
    return amazonS3.getUrl(bucketName, fileName).toString();
  }

  @Override
  public void deleteFile(String fileName) {
    amazonS3.deleteObject(bucketName, fileName);
  }

  private File convertMultiPartFileToFile(MultipartFile file) {
    File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
    try (FileOutputStream out = new FileOutputStream(convertedFile)) {
      out.write(file.getBytes());
    } catch (IOException e) {
      logger.error("Error converting multipartFile to file");
    }
    return convertedFile;
  }
}
