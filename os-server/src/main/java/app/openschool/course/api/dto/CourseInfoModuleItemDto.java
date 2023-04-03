package app.openschool.course.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CourseInfoModuleItemDto {

  @Schema(description = "Type of the module item", example = "Reading")
  private final String moduleItemType;

  @Schema(description = "Link of the module item", example = "S3ds8")
  private final String link;

  @Schema(description = "Duration of the moduleItem (in hours)", example = "2.5")
  private double estimatedTime;

  public CourseInfoModuleItemDto(String moduleItemType, String link, double estimatedTime) {
    this.moduleItemType = moduleItemType;
    this.link = link;
    this.estimatedTime = estimatedTime;
  }

  public String getModuleItemType() {
    return moduleItemType;
  }

  public String getLink() {
    return link;
  }

  public double getEstimatedTime() {
    return estimatedTime;
  }

  public void setEstimatedTime(double estimatedTime) {
    this.estimatedTime = estimatedTime;
  }
}
