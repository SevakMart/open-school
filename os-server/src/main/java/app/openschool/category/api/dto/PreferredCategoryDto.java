package app.openschool.category.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class PreferredCategoryDto {
  @Schema(description = "Category Id", example = "1")
  private Long id;

  @Schema(description = "Category title", example = "Software-Development")
  private String title;

  public PreferredCategoryDto() {}

  public PreferredCategoryDto(Long id, String title) {
    this.id = id;
    this.title = title;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
