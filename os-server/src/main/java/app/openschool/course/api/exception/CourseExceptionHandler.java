package app.openschool.course.api.exception;

import app.openschool.common.response.ResponseMessage;
import java.util.Locale;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CourseExceptionHandler implements ErrorController {

  private final MessageSource messageSource;

  public CourseExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(EnrolledCourseExistsException.class)
  public ResponseEntity<ResponseMessage> handleEnrolledCourseExistsException(Locale locale) {
    String message = messageSource.getMessage("exception.enrolled.course.exists", null, locale);
    return ResponseEntity.badRequest().body(new ResponseMessage(message));
  }
}
