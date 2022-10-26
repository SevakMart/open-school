package app.openschool.common.exception;

import app.openschool.common.response.ResponseMessage;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

@RestControllerAdvice
public class CommonExceptionHandler implements ErrorController {
  private final MessageSource messageSource;

  public CommonExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResponseMessage> handleIncorrectArgumentException(
      IllegalArgumentException ex, Locale locale) {

    String message;
    if (ex.getMessage() == null) {
      message = messageSource.getMessage("incorrect.argument", null, locale);
    } else {
      message = ex.getMessage();
    }
    return ResponseEntity.badRequest().body(new ResponseMessage(message));
  }

  @ExceptionHandler(MissingPathVariableException.class)
  public ResponseEntity<ResponseMessage> handleMissingPathVariableException(Locale locale) {
    String message = messageSource.getMessage("incorrect.argument", null, locale);
    return ResponseEntity.badRequest().body(new ResponseMessage(message));
  }

  @ExceptionHandler(UnsupportedOperationException.class)
  public ResponseEntity<ResponseMessage> handleUnsupportedOperationException(
      UnsupportedOperationException ex) {
    return ResponseEntity.badRequest().body(new ResponseMessage(ex.getMessage()));
  }

  @ExceptionHandler(BindException.class)
  public ResponseEntity<Map<String, String>> handleBindException(BindException bindException) {
    Map<String, String> errorMap = new HashMap<>();
    bindException
        .getAllErrors()
        .forEach(
            objectError -> {
              errorMap.put(((FieldError) objectError).getField(), objectError.getDefaultMessage());
            });
    return ResponseEntity.badRequest().body(errorMap);
  }

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<HttpStatus> handleHttpRequestMethodNotSupportedException(
      HttpRequestMethodNotSupportedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
  }
}
