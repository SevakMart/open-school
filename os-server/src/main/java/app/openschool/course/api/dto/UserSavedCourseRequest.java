package app.openschool.course.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;

public class UserSavedCourseRequest {

  @Schema(description = "Course Id", example = "1")
  @NotNull
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
