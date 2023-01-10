package app.openschool.common.services.aws;

import app.openschool.common.exceptionhandler.exception.CustomIoException;
import app.openschool.common.exceptionhandler.exception.FileDeleteException;
import app.openschool.common.exceptionhandler.exception.FileNotFoundException;
import app.openschool.common.exceptionhandler.exception.FileSaveException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.Objects;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


@Service
@Profile(value = {"local", "test"})
public class FileStorageLocalService implements FileStorageService {
  @Value("${file-pictures-dir}")
  private String picturesDir;

  private final MessageSource messageSource;

  private static final Logger logger = LoggerFactory.getLogger("FileStorageLocalService");

  public FileStorageLocalService(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @Override
  public String uploadFile(MultipartFile multipartFile) {

    Path resolve = null;
    File file = createDataFolder();

    try (InputStream inputStream = multipartFile.getInputStream()) {
      resolve =
          Paths.get(file.getPath())
              .resolve(
                  UUID.randomUUID() + Objects.requireNonNull(multipartFile.getOriginalFilename()));

      long byteCount = Files.copy(inputStream, resolve, StandardCopyOption.REPLACE_EXISTING);
      if (byteCount == 0) {
        throw new FileSaveException(
            messageSource.getMessage("exception.file.not.save", null, Locale.ROOT));
      }
    } catch (IOException ex) {
      throw new CustomIoException(messageSource.getMessage(ex.getMessage(), null, Locale.ROOT));
    }
    return resolve.getFileName().toString();
  }

  @Override
  public void deleteFile(String fileName) {
    try {
      Path path = Paths.get(picturesDir + fileName);
      boolean deleted = Files.deleteIfExists(path);
      if (!deleted) {
        throw new FileDeleteException(
            messageSource.getMessage("exception.file.not.deleted", null, Locale.ROOT));
      }
    } catch (IOException e) {
      throw new FileDeleteException(messageSource.getMessage(e.getMessage(), null, Locale.ROOT));
    }
  }

  @EventListener(ApplicationReadyEvent.class)
  private File createDataFolder() {
    File file = new File(picturesDir);
    if (!file.exists()) {
      boolean isCreatedFolders = file.mkdirs();
      if (!isCreatedFolders) {
        throw new FileNotFoundException(
                messageSource.getMessage("exception.file.not.found", null, Locale.ROOT));
      }
    }

    return file;
  }
}
