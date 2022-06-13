package app.openschool.course.api.dto;

import javax.validation.constraints.NotNull;

public class UserSavedCourseRequest {

  @NotNull(message = "{incorrect.argument}")
  private Long courseId;

  public UserSavedCourseRequest() {}

  public UserSavedCourseRequest(Long courseId) {
    this.courseId = courseId;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }
}
