package app.openschool.course.module.api.dto;

import app.openschool.course.module.item.api.dto.EnrolledModuleItemOverviewDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class EnrolledModuleOverviewDto {

  @Schema(description = "Course title", example = "Collection")
  private final String title;

  @Schema(description = "Current state of the module", example = "In progress")
  private final String status;

  @Schema(description = "Sum of the module moduleItems estimated times", example = "310")
  private final long estimatedTime;

  @ArraySchema(schema = @Schema(implementation = EnrolledModuleItemOverviewDto.class))
  private final Set<EnrolledModuleItemOverviewDto> enrolledModulesItems;

  public EnrolledModuleOverviewDto(
      String title,
      String status,
      long estimatedTime,
      Set<EnrolledModuleItemOverviewDto> enrolledModulesItems) {
    this.title = title;
    this.status = status;
    this.estimatedTime = estimatedTime;
    this.enrolledModulesItems = enrolledModulesItems;
  }

  public String getTitle() {
    return title;
  }

  public String getStatus() {
    return status;
  }

  public long getEstimatedTime() {
    return estimatedTime;
  }

  public Set<EnrolledModuleItemOverviewDto> getEnrolledModulesItems() {
    return enrolledModulesItems;
  }
}
