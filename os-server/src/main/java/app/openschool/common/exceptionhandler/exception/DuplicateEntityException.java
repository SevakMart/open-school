package app.openschool.common.exceptionhandler.exception;

public class DuplicateEntityException extends RuntimeException {

  public DuplicateEntityException(String message) {
    super(message);
  }
}
