package app.openschool.usermanagement.api.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import org.springframework.http.HttpStatus;

/** Useful Javadoc. */
public class UserRegistrationHttpResponse {

  private String timeStamp;
  private HttpStatus httpStatus;
  private String message;
  private Map<String, String> validationErrors;

  public UserRegistrationHttpResponse() {}

  /** Useful Javadoc. */
  public UserRegistrationHttpResponse(HttpStatus httpStatus, String message) {
    this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    this.httpStatus = httpStatus;
    this.message = message;
  }

  /** Useful Javadoc. */
  public UserRegistrationHttpResponse(
      HttpStatus httpStatus, String message, Map<String, String> validationErrors) {
    this.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
    this.httpStatus = httpStatus;
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

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public void setHttpStatus(HttpStatus httpStatus) {
    this.httpStatus = httpStatus;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
