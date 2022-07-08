package app.openschool.category.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoryDto {
  @Schema(description = "Category title", example = "Software Engineering")
  private String title;

  @Schema(description = "Path of the category logo", example = "S3554lst")
  private String logoPath;

  @Schema(description = "Count of subcategories", example = "3")
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
