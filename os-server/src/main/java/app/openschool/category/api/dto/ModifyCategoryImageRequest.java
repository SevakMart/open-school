package app.openschool.category.api.dto;

import app.openschool.common.annotation.MaxFileSize;
import javax.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

public class ModifyCategoryImageRequest {

  @NotNull(message = "{category.image.blank}")
  @MaxFileSize(maxSizeInMb = 3)
  private MultipartFile image;

  public ModifyCategoryImageRequest() {}

  public ModifyCategoryImageRequest(MultipartFile image) {
    this.image = image;
  }

  public MultipartFile getImage() {
    return image;
  }

  public void setImage(MultipartFile image) {
    this.image = image;
  }
}
