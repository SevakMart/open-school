package app.openschool.usermanagement.api.exceptions;

public class EmailAlreadyExistException extends RuntimeException {

  public EmailAlreadyExistException(String message) {
    super(message);
  }
}