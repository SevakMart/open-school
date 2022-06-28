package app.openschool.course.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CourseInfoModuleItemDto {

  @Schema(description = "Type of the module item", example = "Reading")
  private final String moduleItemType;

  @Schema(description = "Link of the module item", example = "S3ds8")
  private final String link;

  public CourseInfoModuleItemDto(String moduleItemType, String link) {
    this.moduleItemType = moduleItemType;
    this.link = link;
  }

  public String getModuleItemType() {
    return moduleItemType;
  }

  public String getLink() {
    return link;
  }
}
