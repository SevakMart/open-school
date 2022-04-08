package app.openschool.usermanagement.api.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import app.openschool.usermanagement.api.dto.UserRegistrationHttpResponse;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandling implements ErrorController {
  private static final String VALIDATION_ERROR = "validation errors";

  @ExceptionHandler(EmailAlreadyExistException.class)
  public ResponseEntity<UserRegistrationHttpResponse> emailExistException(
      EmailAlreadyExistException exception) {
    return createHttpResponse(BAD_REQUEST, exception.getMessage(), null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<UserRegistrationHttpResponse> validationFailedException(
      MethodArgumentNotValidException exception) {
    Map<String, String> errors = getValidationErrors(exception);
    return createHttpResponse(BAD_REQUEST, VALIDATION_ERROR, errors);
  }

  private Map<String, String> getValidationErrors(MethodArgumentNotValidException exception) {
    return exception.getBindingResult().getAllErrors().stream()
        .collect(
            Collectors.toMap(
                error -> ((FieldError) error).getField(), ObjectError::getDefaultMessage));
  }

  private ResponseEntity<UserRegistrationHttpResponse> createHttpResponse(
      HttpStatus httpStatus, String message, Map<String, String> validationErrors) {
    UserRegistrationHttpResponse httpResponse =
        new UserRegistrationHttpResponse(message.toUpperCase(Locale.ROOT), validationErrors);

    return new ResponseEntity<>(httpResponse, httpStatus);
  }
}