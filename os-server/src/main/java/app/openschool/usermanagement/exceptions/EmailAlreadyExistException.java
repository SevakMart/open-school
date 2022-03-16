package app.openschool.usermanagement.exceptions;

public class EmailAlreadyExistException extends RuntimeException {

  public EmailAlreadyExistException(String message) {
    super(message);
  }
}
