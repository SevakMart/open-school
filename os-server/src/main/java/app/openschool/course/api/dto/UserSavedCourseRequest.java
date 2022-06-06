package app.openschool.course.api.dto;

public class UserSavedCourseRequest {

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
