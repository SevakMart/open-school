package app.openschool.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import java.util.HashMap;
import org.springframework.http.HttpStatus;

/** Useful Javadoc. */
public class UserRegistrationHttpResponse {

  @JsonFormat(
      shape = JsonFormat.Shape.STRING,
      pattern = "MM-dd-yyyy hh:mm:ss",
      timezone = "Asia/Yerevan")
  private Date timeStamp;

  private int httpStatusCode;
  private HttpStatus httpStatus;
  private String message;
  private HashMap<String, String> validationErrors;

  public UserRegistrationHttpResponse() {}

  /** Useful Javadoc. */
  public UserRegistrationHttpResponse(int httpStatusCode, HttpStatus httpStatus, String message) {
    this.timeStamp = new Date();
    this.httpStatusCode = httpStatusCode;
    this.httpStatus = httpStatus;
    this.message = message;
  }

  /** Useful Javadoc. */
  public UserRegistrationHttpResponse(
      int httpStatusCode,
      HttpStatus httpStatus,
      String message,
      HashMap<String, String> validationErrors) {
    this.timeStamp = new Date();
    this.httpStatusCode = httpStatusCode;
    this.httpStatus = httpStatus;
    this.message = message;
    this.validationErrors = validationErrors;
  }

  public HashMap<String, String> getValidationErrors() {
    return validationErrors;
  }

  public void setValidationErrors(HashMap<String, String> validationErrors) {
    this.validationErrors = validationErrors;
  }

  public Date getTimeStamp() {
    return timeStamp;
  }

  public void setTimeStamp(Date timeStamp) {
    this.timeStamp = timeStamp;
  }

  public int getHttpStatusCode() {
    return httpStatusCode;
  }

  public void setHttpStatusCode(int httpStatusCode) {
    this.httpStatusCode = httpStatusCode;
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
