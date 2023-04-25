package app.openschool.course.discussion.dto.basedto;

import app.openschool.user.api.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

public abstract class ResponseDto {

  @Schema(description = "id", example = "1")
  private Long id;

  private String text;

  @Schema(description = "An object containing information about the user who gave the answer")
  private UserDto userDto;

  @Schema(description = "Date of creation or update")
  private Instant createdDate;

  protected ResponseDto() {}

  protected ResponseDto(Long id, String text, UserDto userDto, Instant createdDate) {
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
}
