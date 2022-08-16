package app.openschool.category.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

public class CreateCategoryRequest {

  @NotBlank(message = "{category.title.blank}")
  @Length(max = 45, message = "{category.title.long}")
  private String title;

  @Nullable private Long parentCategoryId;

  @NotNull(message = "{category.image.blank}")
  private MultipartFile image;

  public CreateCategoryRequest() {}

  public CreateCategoryRequest(String title, @Nullable Long parentCategoryId, MultipartFile image) {
    this.title = title;
    this.parentCategoryId = parentCategoryId;
    this.image = image;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  @Nullable
  public Long getParentCategoryId() {
    return parentCategoryId;
  }

  public void setParentCategoryId(@Nullable Long parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
  }

  public MultipartFile getImage() {
    return image;
  }

  public void setImage(MultipartFile image) {
    this.image = image;
  }
}
