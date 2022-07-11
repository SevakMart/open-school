package app.openschool.course.module.item.api;

import io.swagger.v3.oas.annotations.media.Schema;

public class EnrolledModuleItemOverviewDto {

  @Schema(description = "Course id", example = "1")
  private final Long id;

  @Schema(description = "Course type", example = "Video")
  private final String type;

  @Schema(description = "Course name", example = "List")
  private final String name;

  @Schema(description = "Value of time assumed to complete the moduleItem", example = "310")
  private final long estimatedTime;

  @Schema(description = "ModuleItem link", example = "S3jl47")
  private final String link;

  @Schema(description = "Current state of the moduleItem", example = "Completed")
  private final String status;

  public EnrolledModuleItemOverviewDto(
      Long id, String type, String name, long estimatedTime, String link, String status) {
    this.id = id;
    this.type = type;
    this.name = name;
    this.estimatedTime = estimatedTime;
    this.link = link;
    this.status = status;
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public long getEstimatedTime() {
    return estimatedTime;
  }

  public String getLink() {
    return link;
  }

  public String getStatus() {
    return status;
  }

  public Long getId() {
    return id;
  }
}
