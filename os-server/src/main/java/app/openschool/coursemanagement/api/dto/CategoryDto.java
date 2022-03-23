package app.openschool.coursemanagement.api.dto;

public class CategoryDto {

  private String title;
  private String logoPath;
  private Integer subCategoryCount;

  public CategoryDto() {}

  public CategoryDto(String title, String logoPath) {
    this.title = title;
    this.logoPath = logoPath;
  }

  public CategoryDto(String title, String logoPath, Integer subCategoryCount) {
    this.title = title;
    this.logoPath = logoPath;
    this.subCategoryCount = subCategoryCount;
  }

  public String getTitle() {
    return title;
  }

  public String getLogoPath() {
    return logoPath;
  }

  public Integer getSubCategoryCount() {
    return subCategoryCount;
  }
}
