package app.openschool.common.services.aws.storage;

import org.springframework.web.multipart.MultipartFile;

public interface FileStorageService {

  String uploadFile(MultipartFile file);

  void deleteFile(String fileName);
}
