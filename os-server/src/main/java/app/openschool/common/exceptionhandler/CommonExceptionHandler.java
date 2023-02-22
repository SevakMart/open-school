package app.openschool.common.exceptionhandler;

import app.openschool.auth.api.exception.TokenValidationException;
import app.openschool.category.api.exception.CategoryNestingException;
import app.openschool.common.exceptionhandler.exception.DuplicateEntityException;
import app.openschool.common.exceptionhandler.exception.FileDeleteException;
import app.openschool.common.exceptionhandler.exception.FileNotFoundException;
import app.openschool.common.exceptionhandler.exception.FileSaveException;
import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.common.exceptionhandler.exception.TemporaryStorageFailsException;
import app.openschool.common.response.ResponseMessage;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonExceptionHandler implements ErrorController {
  private final MessageSource messageSource;

  public CommonExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResponseMessage> handleIncorrectArgumentException(Locale locale) {
    String message = messageSource.getMessage("incorrect.argument", null, locale);
    return ResponseEntity.badRequest().body(new ResponseMessage(message));
  }

  @ExceptionHandler(DuplicateEntityException.class)
  public ResponseEntity<ResponseMessage> handleValidationException(DuplicateEntityException ex) {
    return ResponseEntity.badRequest().body(new ResponseMessage(ex.getMessage()));
  }

  @ExceptionHandler(CategoryNestingException.class)
  public ResponseEntity<ResponseMessage> handleCategoryNestingException(
      CategoryNestingException ex) {
    return ResponseEntity.badRequest().body(new ResponseMessage(ex.getMessage()));
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

  @ExceptionHandler(FileSaveException.class)
  public ResponseEntity<ResponseMessage> handleFileSaveException(FileSaveException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(ex.getMessage()));
  }

  @ExceptionHandler
  public ResponseEntity<ResponseMessage> handleFileDeleteException(FileDeleteException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(ex.getMessage()));
  }

  @ExceptionHandler(FileNotFoundException.class)
  public ResponseEntity<ResponseMessage> handleFileNotFoundException(FileNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(ex.getMessage()));
  }

  @ExceptionHandler(TemporaryStorageFailsException.class)
  public ResponseEntity<ResponseMessage> handleCustomIoException(
      TemporaryStorageFailsException ex) {
    return ResponseEntity.status(HttpStatus.CONFLICT).body(new ResponseMessage(ex.getMessage()));
  }

  @ExceptionHandler(PermissionDeniedException.class)
  public ResponseEntity<ResponseMessage> handlePermissionDeniedException(
      PermissionDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(ex.getMessage()));
  }

  @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
  public ResponseEntity<ResponseMessage> handleSqlIntegrityConstraintViolationException(
      SQLIntegrityConstraintViolationException ex) {
    String message = messageSource.getMessage("exception.persist", null, Locale.ROOT);
    return ResponseEntity.badRequest().body(new ResponseMessage(message));
  }

  @ExceptionHandler(AccessDeniedException.class)
  public ResponseEntity<ResponseMessage> handleAccessDeniedException(AccessDeniedException ex) {
    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ResponseMessage(ex.getMessage()));
  }

  @ExceptionHandler(TokenValidationException.class)
  public ResponseEntity<ResponseMessage> handleTokenValidationException(
          TokenValidationException ex) {
    String message = messageSource.getMessage("exception.token.verification", null, Locale.ROOT);
    return ResponseEntity.badRequest().body(new ResponseMessage(message));
  }
}
