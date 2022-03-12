package app.openschool.user.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import app.openschool.user.dto.UserRegistrationHttpResponse;
import java.util.HashMap;
import java.util.Locale;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


/** Useful Javadoc. */
@RestControllerAdvice
public class UserExceptionHandling implements ErrorController {
  private static final String VALIDATION_ERROR = "validation errors";

  @ExceptionHandler(EmailAlreadyExistException.class)
  public ResponseEntity<UserRegistrationHttpResponse> emailExistException(
      EmailAlreadyExistException exception) {
    return createHttpResponse(BAD_REQUEST, exception.getMessage());
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<UserRegistrationHttpResponse> validationFailedException(
      MethodArgumentNotValidException exception) {
    HashMap<String, String> errors = getValidationErrors(exception);
    return createHttpResponse(BAD_REQUEST, VALIDATION_ERROR, errors);
  }

  private HashMap<String, String> getValidationErrors(MethodArgumentNotValidException exception) {
    HashMap<String, String> errors = new HashMap<>();
    exception
        .getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              String errorMessage = error.getDefaultMessage();
              errors.put(fieldName, errorMessage);
            });
    return errors;
  }

  private ResponseEntity<UserRegistrationHttpResponse> createHttpResponse(
      HttpStatus httpStatus, String message) {
    UserRegistrationHttpResponse httpResponse =
        new UserRegistrationHttpResponse(
            httpStatus.value(), httpStatus, message.toUpperCase(Locale.ROOT));

    return new ResponseEntity<>(httpResponse, httpStatus);
  }

  private ResponseEntity<UserRegistrationHttpResponse> createHttpResponse(
      HttpStatus httpStatus, String message, HashMap<String, String> validationErrors) {
    UserRegistrationHttpResponse httpResponse =
        new UserRegistrationHttpResponse(
            httpStatus.value(), httpStatus, message.toUpperCase(Locale.ROOT), validationErrors);

    return new ResponseEntity<>(httpResponse, httpStatus);
  }
}
