package app.openschool.common.services.aws;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



@Service
@Profile(value = {"local", "test"})
public class FileStorageLocalService implements FileStorageService {
  @Value("${file-pictures-dir}")
  private String picturesDir;

  private final Logger logger = LoggerFactory.getLogger("FileStorageLocalService");

  @Override
  public String uploadFile(MultipartFile multipartFile) {

    Path resolve = null;
    File file = new File(picturesDir);
    if (file.exists()) {
      try (InputStream inputStream = multipartFile.getInputStream()) {
        resolve =
            Paths.get(file.getPath())
                .resolve(
                    UUID.randomUUID()
                        + Objects.requireNonNull(multipartFile.getOriginalFilename()));

        Files.copy(inputStream, resolve, StandardCopyOption.REPLACE_EXISTING);
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
    return resolve != null ? resolve.getFileName().toString() : null;
  }

  @Override
  public void deleteFile(String fileName) {
    try {
      Path path = Paths.get(picturesDir + fileName);
      Files.deleteIfExists(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @EventListener(ApplicationReadyEvent.class)
  private void createDataFolder() {
    File file = new File(picturesDir);
    if (!file.exists()) {
      file.mkdir();
    }
  }
}
