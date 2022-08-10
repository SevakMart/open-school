package app.openschool.category.api.dto;

public class CreateOrModifyCategoryRequest {

  private String title;

  private Long parentCategoryId;

  public CreateOrModifyCategoryRequest() {}

  public CreateOrModifyCategoryRequest(String title, Long parentCategoryId) {
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
