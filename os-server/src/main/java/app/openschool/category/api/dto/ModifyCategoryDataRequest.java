package app.openschool.category.api.dto;

import org.hibernate.validator.constraints.Length;
import org.springframework.lang.Nullable;

public class ModifyCategoryDataRequest {

  @Nullable
  @Length(max = 45, message = "{category.title.long}")
  private String title;

  @Nullable private Long parentCategoryId;

  public ModifyCategoryDataRequest() {}

  public ModifyCategoryDataRequest(@Nullable String title, @Nullable Long parentCategoryId) {
    this.title = title;
    this.parentCategoryId = parentCategoryId;
  }

  @Nullable
  public String getTitle() {
    return title;
  }

  public void setTitle(@Nullable String title) {
    this.title = title;
  }

  @Nullable
  public Long getParentCategoryId() {
    return parentCategoryId;
  }

  public void setParentCategoryId(@Nullable Long parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
  }
}
