package app.openschool.course.discussion.dto;

import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.discussion.dto.basedto.ResponseDto;
import app.openschool.user.api.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

public class QuestionResponseDto extends ResponseDto {

  @Schema(description = "Question", example = "Any question")
  @Override
  public String getText() {
    return super.getText();
  }

  @Schema(
      description =
          "An object containing information about the course to which the question relates")
  private CourseDto courseDto;

  public QuestionResponseDto() {}

  public QuestionResponseDto(
      Long id, String text, UserDto userDto, CourseDto courseDto, Instant createdDate) {
    super(id, text, userDto, createdDate);
    this.courseDto = courseDto;
  }

  public CourseDto getCourseDto() {
    return courseDto;
  }

  public void setCourseDto(CourseDto courseDto) {
    this.courseDto = courseDto;
  }
}
