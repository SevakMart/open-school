package app.openschool.auth.exception;

public class EmailNotExistsException extends RuntimeException {
  public EmailNotExistsException(String message) {
    super(message);
  }
}
