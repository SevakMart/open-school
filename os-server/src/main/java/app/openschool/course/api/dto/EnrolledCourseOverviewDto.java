package app.openschool.course.api.dto;

import app.openschool.course.module.api.EnrolledModuleOverviewDto;
import java.util.List;
import java.util.Set;

public class EnrolledCourseOverviewDto {

  private final String courseTitle;

  private final String courseStatus;

  private final int grade;

  private final long courseEstimatedTime;

  private final Set<EnrolledModuleOverviewDto> enrolledModules;

  public EnrolledCourseOverviewDto(
      String courseTitle,
      String courseStatus,
      int grade,
      long courseEstimatedTime,
      Set<EnrolledModuleOverviewDto> enrolledModules) {
    this.courseTitle = courseTitle;
    this.courseStatus = courseStatus;
    this.grade = grade;
    this.courseEstimatedTime = courseEstimatedTime;
    this.enrolledModules = enrolledModules;
  }

  public String getCourseTitle() {
    return courseTitle;
  }

  public String getCourseStatus() {
    return courseStatus;
  }

  public int getGrade() {
    return grade;
  }

  public long getCourseEstimatedTime() {
    return courseEstimatedTime;
  }

  public Set<EnrolledModuleOverviewDto> getEnrolledModules() {
    return enrolledModules;
  }
}
