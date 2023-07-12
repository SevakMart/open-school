package app.openschool.common.exceptionhandler.exception;

public class InvalidSearchQueryException extends RuntimeException {
  public InvalidSearchQueryException(String message) {
    super(message);
  }
}
