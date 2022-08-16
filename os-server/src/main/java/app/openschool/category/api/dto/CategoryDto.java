package app.openschool.category.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CategoryDto {

  @Schema(description = "Category id", example = "1")
  private Long id;

  @Schema(description = "Category title", example = "Software Engineering")
  private String title;

  @Schema(description = "Path of the category logo", example = "S3554lst")
  private String logoPath;

  public CategoryDto() {}

  public CategoryDto(Long id, String title, String logoPath) {
    this.id = id;
    this.title = title;
    this.logoPath = logoPath;
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

  public String getLogoPath() {
    return logoPath;
  }

  public void setLogoPath(String logoPath) {
    this.logoPath = logoPath;
  }

  @Override
  public int hashCode() {
    int hashcode = this.id == null ? 0 : (this.id).intValue();
    hashcode = hashcode * 31 + (this.title == null ? 0 : this.title.hashCode());
    return hashcode;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (!(obj instanceof CategoryDto)) {
      return false;
    }
    CategoryDto other = (CategoryDto) obj;
    return this.id != null
        && this.id.equals(other.id)
        && this.title != null
        && this.title.equals(other.title);
  }

  @Override
  public String toString() {
    return "CategoryDto{"
        + "id="
        + id
        + ", title='"
        + title
        + '\''
        + ", logoPath='"
        + logoPath
        + '\''
        + '}';
  }
}
