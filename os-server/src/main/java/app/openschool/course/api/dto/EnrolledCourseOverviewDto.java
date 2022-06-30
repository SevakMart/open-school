package app.openschool.course.api.dto;

import app.openschool.course.module.api.EnrolledModuleOverviewDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class EnrolledCourseOverviewDto {

  @Schema(description = "Course title", example = "Java")
  private String courseTitle;

  @Schema(description = "Current state of the course", example = "In progress")
  private String courseStatus;

  @Schema(description = "Sum of the course modules estimated times", example = "310")
  private long courseEstimatedTime;

  @ArraySchema(schema = @Schema(implementation = EnrolledModuleOverviewDto.class))
  private Set<EnrolledModuleOverviewDto> enrolledModules;

  public EnrolledCourseOverviewDto() {}

  public EnrolledCourseOverviewDto(
      String courseTitle,
      String courseStatus,
      long courseEstimatedTime,
      Set<EnrolledModuleOverviewDto> enrolledModules) {
    this.courseTitle = courseTitle;
    this.courseStatus = courseStatus;
    this.courseEstimatedTime = courseEstimatedTime;
    this.enrolledModules = enrolledModules;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public String getCourseStatus() {
    return courseStatus;
  }

  public long getCourseEstimatedTime() {
    return courseEstimatedTime;
  }

  public Set<EnrolledModuleOverviewDto> getEnrolledModules() {
    return enrolledModules;
  }
}
