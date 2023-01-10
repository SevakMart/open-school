package app.openschool.common.services;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.times;

import app.openschool.common.exceptionhandler.exception.CustomIoException;
import app.openschool.common.exceptionhandler.exception.FileDeleteException;
import app.openschool.common.exceptionhandler.exception.FileNotFoundException;
import app.openschool.common.exceptionhandler.exception.FileSaveException;
import app.openschool.common.services.aws.FileStorageLocalService;
import app.openschool.common.services.aws.FileStorageService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Locale;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.multipart.MultipartFile;


@ExtendWith(MockitoExtension.class)
class FileStorageLocalServiceTest {
  @Spy FileStorageService fileStorageService;
  @Mock MessageSource messageSource;
  @Mock MultipartFile multipartFile;
  MockedStatic<Files> utilities;
  File tmpFile;

  @BeforeEach
  void setUp() {
    try {
      tmpFile = File.createTempFile("image", ".pnj");
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    fileStorageService = new FileStorageLocalService(messageSource);
    String picturesDir =
        System.getProperty("user.home")
            + File.separator
            + "data"
            + File.separator
            + "categories"
            + File.separator
            + "images";
    ReflectionTestUtils.setField(fileStorageService, "picturesDir", picturesDir);
    utilities = mockStatic(Files.class);
  }

  @AfterEach
  void close() {
    utilities.close();
    tmpFile.deleteOnExit();
  }

  @Test
  void uploadFile_returnFileName() {

    MockMultipartFile multipartFile = createMockFile();
    utilities.when(() -> Files.copy((InputStream) any(), any(), any())).thenReturn(1L);
    String fileName = fileStorageService.uploadFile(multipartFile);
    assertEquals(
        fileName != null ? fileName.substring(fileName.length() - 8) : null,
        multipartFile.getOriginalFilename());
  }

  @Test
  void uploadFile_whenDataFolderDoeNotExist() {
    ReflectionTestUtils.setField(fileStorageService, "picturesDir", "");
    MockMultipartFile multipartFile = createMockFile();
    assertThatThrownBy(() -> fileStorageService.uploadFile(multipartFile))
        .isInstanceOf(FileNotFoundException.class);
  }

  @Test
  void uploadFile_CanNotSaveFile() {
    MockMultipartFile multipartFile = createMockFile();

    utilities.when(() -> Files.copy((InputStream) any(), any(), any())).thenReturn(0L);
    assertThatThrownBy(() -> fileStorageService.uploadFile(multipartFile))
        .isInstanceOf(FileSaveException.class);
  }

  @Test
  void uploadFile_catchIoException() {
    try {
      Mockito.when(multipartFile.getInputStream()).thenThrow(new IOException());
    } catch (IOException e) {
      throw new CustomIoException(messageSource.getMessage(e.getMessage(), null, Locale.ROOT));
    }
    assertThatThrownBy(() -> fileStorageService.uploadFile(multipartFile))
        .isInstanceOf(CustomIoException.class);
  }

  @Test
  public void deleteFileSuccessfully() {
    utilities.when(() -> Files.deleteIfExists(any())).thenReturn(true);
    fileStorageService.deleteFile(anyString());
    utilities.verify(() -> Files.deleteIfExists(any()), times(1));
  }

  @Test
  void deleteFileFailure() {
    utilities.when(() -> Files.deleteIfExists(any())).thenReturn(false);
    assertThatThrownBy(() -> fileStorageService.deleteFile(anyString()))
        .isInstanceOf(FileDeleteException.class);
  }

  @Test
  void deleteFile_throwIoexception() {
    utilities.when(() -> Files.deleteIfExists(any())).thenThrow(new IOException());
    assertThatThrownBy(() -> fileStorageService.deleteFile(anyString()))
        .isInstanceOf(FileDeleteException.class);
  }

  private static MockMultipartFile createMockFile() {
    return new MockMultipartFile("file", "test.png", "png", "open_school".getBytes());
  }
}
