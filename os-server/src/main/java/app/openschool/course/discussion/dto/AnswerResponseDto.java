package app.openschool.course.discussion.dto;

import app.openschool.user.api.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;
import java.util.Objects;

public class AnswerResponseDto {
  @Schema(description = "id", example = "1")
  private Long id;

  @Schema(description = "Answer", example = "Any answer")
  private String text;

  @Schema(description = "An object containing information about the user who gave the answer")
  private UserDto userDto;

  @Schema(description = "Date of creation")
  private Instant createdDate;

  public AnswerResponseDto() {}

  public AnswerResponseDto(Long id, String text, UserDto userDto, Instant createdDate) {
    this.id = id;
    this.text = text;
    this.userDto = userDto;
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
    AnswerResponseDto that = (AnswerResponseDto) o;
    return Objects.equals(id, that.id)
        && Objects.equals(text, that.text)
        && Objects.equals(userDto, that.userDto)
        && Objects.equals(createdDate, that.createdDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, text, userDto, createdDate);
  }
}
