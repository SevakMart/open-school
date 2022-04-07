package app.openschool.coursemanagement.api.dto;

import java.time.LocalDate;

public class CourseOfUserDto {

  private String title;

  private String courseStatus;

  private Long percentage;

  private String remainingTime;

  private Integer grade;

  private LocalDate dueDate;

  private String buttonName;

  public CourseOfUserDto() {}

  public CourseOfUserDto(String title, String courseStatus, Integer grade, String buttonName) {
    this.title = title;
    this.courseStatus = courseStatus;
    this.grade = grade;
    this.buttonName = buttonName;
  }

  public CourseOfUserDto(
      String title,
      String courseStatus,
      Long percentage,
      String remainingTime,
      LocalDate dueDate,
      String buttonName) {
    this.title = title;
    this.courseStatus = courseStatus;
    this.percentage = percentage;
    this.remainingTime = remainingTime;
    this.dueDate = dueDate;
    this.buttonName = buttonName;
  }

  public CourseOfUserDto(
      String title,
      String courseStatus,
      Long percentage,
      String remainingTime,
      Integer grade,
      LocalDate dueDate,
      String buttonName) {
    this.title = title;
    this.courseStatus = courseStatus;
    this.percentage = percentage;
    this.remainingTime = remainingTime;
    this.grade = grade;
    this.dueDate = dueDate;
    this.buttonName = buttonName;
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

  public String getRemainingTime() {
    return remainingTime;
  }

  public void setRemainingTime(String remainingTime) {
    this.remainingTime = remainingTime;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }

  public String getButtonName() {
    return buttonName;
  }

  public void setButtonName(String buttonName) {
    this.buttonName = buttonName;
  }

  public Integer getGrade() {
    return grade;
  }

  public void setGrade(Integer grade) {
    this.grade = grade;
  }
}
