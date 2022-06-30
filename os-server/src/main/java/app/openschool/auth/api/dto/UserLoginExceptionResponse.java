package app.openschool.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class UserLoginExceptionResponse {

  @Schema(description = "Time and date of the message", example = "28-06-2022 15:44:11")
  private String timeStamp;

  @Schema(description = "Message", example = "User with Test888@gmail.com email does not exist")
  private String message;

  public UserLoginExceptionResponse(String message) {
    this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    this.message = message;
  }

  public String getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(String timeStamp) {
    this.timeStamp = timeStamp;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
