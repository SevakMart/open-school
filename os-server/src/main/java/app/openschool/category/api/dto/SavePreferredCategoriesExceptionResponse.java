package app.openschool.category.api.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SavePreferredCategoriesExceptionResponse {
  private String timeStamp;
  private String message;

  public SavePreferredCategoriesExceptionResponse(String message) {
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
