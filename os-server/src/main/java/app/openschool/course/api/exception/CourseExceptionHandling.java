package app.openschool.course.api.exception;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import app.openschool.category.api.dto.SavePreferredCategoriesExceptionResponse;
import app.openschool.category.api.exception.CategoryNotFoundException;
import app.openschool.user.api.exception.UserNotFoundException;
import java.util.Locale;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CourseExceptionHandling implements ErrorController {

  private final MessageSource messageSource;

  public CourseExceptionHandling(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(UserNotFoundException.class)
  public ResponseEntity<SavePreferredCategoriesExceptionResponse> userNotFoundException(
      UserNotFoundException exception, Locale locale) {
    String[] args = {exception.getMessage()};
    String message =
        messageSource.getMessage("exception.nonexistent.user.id.message", args, locale);
    return new ResponseEntity<>(new SavePreferredCategoriesExceptionResponse(message), NOT_FOUND);
  }

  @ExceptionHandler(CategoryNotFoundException.class)
  public ResponseEntity<SavePreferredCategoriesExceptionResponse> categoryNotFoundException(
      CategoryNotFoundException exception, Locale locale) {
    String[] args = {exception.getMessage()};
    String message =
        messageSource.getMessage("exception.nonexistent.category.id.message", args, locale);
    return new ResponseEntity<>(new SavePreferredCategoriesExceptionResponse(message), NOT_FOUND);
  }
}
