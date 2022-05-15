package app.openschool.course.api.dto;

import java.time.LocalDate;

public class UserEnrolledCourseDto {

  private String title;

  private String courseStatus;

  private long percentage;

  private long remainingTime;

  private Integer grade;

  private LocalDate dueDate;

  public UserEnrolledCourseDto() {}

  public UserEnrolledCourseDto(String title, String courseStatus, Integer grade) {
    this.title = title;
    this.courseStatus = courseStatus;
    this.grade = grade;
  }

  public UserEnrolledCourseDto(
      String title,
      String courseStatus,
      long percentage,
      long remainingTime,
      LocalDate dueDate) {
    this.title = title;
    this.courseStatus = courseStatus;
    this.percentage = percentage;
    this.remainingTime = remainingTime;
    this.dueDate = dueDate;
  }

  public UserEnrolledCourseDto(
      String title,
      String courseStatus,
      long percentage,
      long remainingTime,
      Integer grade,
      LocalDate dueDate) {
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

  public void setGrade(Integer grade) {
    this.grade = grade;
  }
}
