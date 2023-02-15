package app.openschool.course.discussion.dto;

import app.openschool.course.api.dto.CourseDto;
import app.openschool.user.api.dto.UserDto;
import java.time.Instant;
import java.util.Objects;

public class QuestionResponseDto {

  private Long id;
  private String text;
  private UserDto userDto;
  private CourseDto courseDto;
  private Instant createdDate;

  public QuestionResponseDto() {}

  public QuestionResponseDto(
      Long id, String text, UserDto userDto, CourseDto courseDto, Instant createdDate) {
    this.id = id;
    this.text = text;
    this.userDto = userDto;
    this.courseDto = courseDto;
    this.createdDate = createdDate;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public UserDto getUserDto() {
    return userDto;
  }

  public void setUserDto(UserDto userDto) {
    this.userDto = userDto;
  }

  public CourseDto getCourseDto() {
    return courseDto;
  }

  public void setCourseDto(CourseDto courseDto) {
    this.courseDto = courseDto;
  }

  public Instant getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Instant createdDate) {
    this.createdDate = createdDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QuestionResponseDto that = (QuestionResponseDto) o;
    return Objects.equals(id, that.id)
        && Objects.equals(text, that.text)
        && Objects.equals(userDto, that.userDto)
        && Objects.equals(courseDto, that.courseDto)
        && Objects.equals(createdDate, that.createdDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, userDto, courseDto, createdDate);
  }
}
