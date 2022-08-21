package app.openschool.category.api.dto;

import org.hibernate.validator.constraints.Length;

public class ModifyCategoryDataRequest {

  @Length(max = 45, message = "{category.title.long}")
  private String title;

  private Long parentCategoryId;

  public ModifyCategoryDataRequest() {}

  public ModifyCategoryDataRequest(String title, Long parentCategoryId) {
    this.title = title;
    this.parentCategoryId = parentCategoryId;
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
}
