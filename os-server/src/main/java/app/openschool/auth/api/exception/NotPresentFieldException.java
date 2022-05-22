package app.openschool.auth.api.exception;

public class NotPresentFieldException extends RuntimeException {
  public NotPresentFieldException(String message) {
    super(message);
  }
}
