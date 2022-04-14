package app.openschool.user.api.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class UserRegistrationHttpResponse {

  private String timeStamp;
  private String message;
  private Map<String, String> validationErrors;

  public UserRegistrationHttpResponse() {}

  public UserRegistrationHttpResponse(String message) {
    this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    this.message = message;
  }

  public UserRegistrationHttpResponse(String message, Map<String, String> validationErrors) {
    this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    this.message = message;
    this.validationErrors = validationErrors;
  }

  public Map<String, String> getValidationErrors() {
    return validationErrors;
  }

  public void setValidationErrors(Map<String, String> validationErrors) {
    this.validationErrors = validationErrors;
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
