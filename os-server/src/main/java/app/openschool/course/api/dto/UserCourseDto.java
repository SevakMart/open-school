package app.openschool.course.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;

public class UserCourseDto {

  @Schema(description = "Course Id", example = "1")
  private Long id;

  @Schema(description = "Course title", example = "Java")
  private String title;

  @Schema(description = "Current state of course", example = "In progress")
  private String courseStatus;

  @Schema(description = "Progress of course in percent", example = "75")
  private long percentage;

  @Schema(description = "Time remained to complete course", example = "15")
  private long remainingTime;

  @Schema(description = "Grade of course", example = "100")
  private Integer grade;

  @Schema(description = "Enrolled course id", example = "1")
  private Long courseId;

  @Schema(
      description = "By the specified date the course must be completed",
      example = "2022-07-11")
  private LocalDate dueDate;

  public UserCourseDto() {}

  public UserCourseDto(Long id, String title, String courseStatus, Integer grade) {
    this.id = id;
    this.title = title;
    this.courseStatus = courseStatus;
    this.grade = grade;
  }

  public UserCourseDto(
      Long id,
      String title,
      String courseStatus,
      long percentage,
      long remainingTime,
      LocalDate dueDate,
      Long courseId) {
    this.id = id;
    this.title = title;
    this.courseStatus = courseStatus;
    this.percentage = percentage;
    this.remainingTime = remainingTime;
    this.dueDate = dueDate;
    this.courseId = courseId;
  }

  public UserCourseDto(
      Long id,
      String title,
      String courseStatus,
      long percentage,
      long remainingTime,
      Integer grade,
      LocalDate dueDate) {
    this.id = id;
    this.title = title;
    this.courseStatus = courseStatus;
    this.percentage = percentage;
    this.remainingTime = remainingTime;
    this.grade = grade;
    this.dueDate = dueDate;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getCourseStatus() {
    return courseStatus;
  }

  public void setCourseStatus(String courseStatus) {
    this.courseStatus = courseStatus;
  }

  public Long getPercentage() {
    return percentage;
  }

  public void setPercentage(Long percentage) {
    this.percentage = percentage;
  }

  public long getRemainingTime() {
    return remainingTime;
  }

  public void setRemainingTime(long remainingTime) {
    this.remainingTime = remainingTime;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public Integer getGrade() {
    return grade;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }

  public void setGrade(Integer grade) {
    this.grade = grade;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
