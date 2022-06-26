package app.openschool.course.api.dto;

import app.openschool.course.module.api.EnrolledModuleOverviewDto;
import java.util.Set;

public class EnrolledCourseOverviewDto {

  private String courseTitle;

  private String courseStatus;

  private long courseEstimatedTime;

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
