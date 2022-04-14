package app.openschool.usermanagement.api.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import app.openschool.usermanagement.api.dto.UserLoginExceptionResponse;
import app.openschool.usermanagement.api.dto.UserRegistrationHttpResponse;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UserExceptionHandling implements ErrorController {

  private final MessageSource messageSource;

  public UserExceptionHandling(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(EmailAlreadyExistException.class)
  public ResponseEntity<UserRegistrationHttpResponse> emailExistException(Locale locale) {
    String message =
        messageSource.getMessage("exception.duplicate.user.email.message", null, locale);
    return createHttpResponse(BAD_REQUEST, message, null);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<UserRegistrationHttpResponse> validationFailedException(
      MethodArgumentNotValidException exception, Locale locale) {
    Map<String, String> errors = getValidationErrors(exception, locale);
    String message = messageSource.getMessage("validation.errors.message", null, locale);
    return createHttpResponse(BAD_REQUEST, message, errors);
  }

  @ExceptionHandler(EmailNotFoundException.class)
  public ResponseEntity<UserLoginExceptionResponse> userEmailNotFoundException(
      EmailNotFoundException exception, Locale locale) {
    String[] args = {exception.getMessage()};
    String message =
        messageSource.getMessage("exception.nonexistent.user.email.message", args, locale);
    return new ResponseEntity<>(new UserLoginExceptionResponse(message), UNAUTHORIZED);
  }

  @ExceptionHandler(BadCredentialsException.class)
  public ResponseEntity<UserLoginExceptionResponse> badCredentialsException(Locale locale) {
    String message = messageSource.getMessage("exception.bad.credentials.message", null, locale);
    return new ResponseEntity<>(new UserLoginExceptionResponse(message), UNAUTHORIZED);
  }

  private Map<String, String> getValidationErrors(
      MethodArgumentNotValidException exception, Locale locale) {
    return exception.getBindingResult().getAllErrors().stream()
        .collect(
            Collectors.toMap(
                error -> ((FieldError) error).getField(),
                objectError -> messageSource.getMessage(objectError, locale)));
  }

  private ResponseEntity<UserRegistrationHttpResponse> createHttpResponse(
      HttpStatus httpStatus, String message, Map<String, String> validationErrors) {
    UserRegistrationHttpResponse httpResponse =
        new UserRegistrationHttpResponse(message.toUpperCase(Locale.ROOT), validationErrors);

    return new ResponseEntity<>(httpResponse, httpStatus);
  }
}
