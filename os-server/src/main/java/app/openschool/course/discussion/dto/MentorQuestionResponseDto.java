package app.openschool.course.discussion.dto;

import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.discussion.dto.basedto.ResponseDto;
import app.openschool.user.api.dto.MentorDto;
import app.openschool.user.api.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

public class MentorQuestionResponseDto extends ResponseDto {

  @Schema(description = "Question", example = "Any question to the Mentor")
  @Override
  public String getText() {
    return super.getText();
  }

  @Schema(
      description = "An object containing information about the mentor who was asked the question")
  private MentorDto mentorDto;

  @Schema(
      description =
          "An object containing information about the course to which the question relates")
  private CourseDto courseDto;

  public MentorQuestionResponseDto() {}

  public MentorQuestionResponseDto(
      Long id,
      String text,
      MentorDto mentorDto,
      UserDto userDto,
      CourseDto courseDto,
      Instant createdDate){
    super(id, text, userDto, createdDate);
    this.mentorDto = mentorDto;
    this.courseDto = courseDto;
  }

  public MentorDto getMentorDto() {
    return mentorDto;
  }

  public void setMentorDto(MentorDto mentorDto) {
    this.mentorDto = mentorDto;
  }

  public CourseDto getCourseDto() {
    return courseDto;
  }

  public void setCourseDto(CourseDto courseDto) {
    this.courseDto = courseDto;
  }
}
