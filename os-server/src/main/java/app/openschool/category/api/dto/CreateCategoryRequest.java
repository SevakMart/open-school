package app.openschool.category.api.dto;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class CreateCategoryRequest {

  @NotBlank(message = "{category.name.blank}")
  @Length(max = 100, message = "{category.name.length}")
  private String title;

  private Long parentCategoryId;

  @NotBlank(message = "{category.logo.blank}")
  @NotBlank private String logoPath;

  public CreateCategoryRequest() {}

  public CreateCategoryRequest(String title, Long parentCategoryId, String logoPath) {
    this.title = title;
    this.parentCategoryId = parentCategoryId;
    this.logoPath = logoPath;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getParentCategoryId() {
    return parentCategoryId;
  }

  public void setParentCategoryId(Long parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
  }

  public String getLogoPath() {
    return logoPath;
  }

  public void setLogoPath(String logoPath) {
    this.logoPath = logoPath;
  }
}
