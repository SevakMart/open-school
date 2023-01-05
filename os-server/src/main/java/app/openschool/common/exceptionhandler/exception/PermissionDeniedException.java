package app.openschool.common.exceptionhandler.exception;

public class PermissionDeniedException extends RuntimeException {

  public PermissionDeniedException(String message) {
    super(message);
  }
}
