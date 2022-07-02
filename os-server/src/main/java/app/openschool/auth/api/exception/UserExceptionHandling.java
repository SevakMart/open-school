package app.openschool.auth.api.exception;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import app.openschool.auth.api.dto.UserLoginExceptionResponse;
import app.openschool.auth.exception.UserNotVerifiedException;
import app.openschool.common.response.ResponseMessage;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@RestControllerAdvice
public class UserExceptionHandling implements ErrorController {

  private final MessageSource messageSource;
  private final ITemplateEngine templateEngine;

  public UserExceptionHandling(MessageSource messageSource, ITemplateEngine templateEngine) {
    this.messageSource = messageSource;
    this.templateEngine = templateEngine;
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

  @ExceptionHandler(UserNotVerifiedException.class)
  public String userNotVerifiedException(Locale locale) {
    String message = messageSource.getMessage("exception.unverified.user.message", null, locale);
    Context context = new Context();
    context.setVariable("message", message);
    return templateEngine.process("verification-response", context);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResponseMessage> handleIncorrectArgumentException(Locale locale) {
    String message = messageSource.getMessage("incorrect.argument", null, locale);
    return ResponseEntity.badRequest().body(new ResponseMessage(message));
  }

  private Map<String, String> getValidationErrors(
      MethodArgumentNotValidException exception, Locale locale) {
    return exception.getBindingResult().getAllErrors().stream()
        .collect(
            Collectors.toMap(
                error -> ((FieldError) error).getField(),
                objectError -> messageSource.getMessage(objectError, locale)));
  }
}
