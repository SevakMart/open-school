package app.openschool.auth.exception;

public class NotMatchingPasswordsException extends RuntimeException {
  public NotMatchingPasswordsException(String message) {
    super(message);
  }
}
