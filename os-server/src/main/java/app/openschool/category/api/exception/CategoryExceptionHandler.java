package app.openschool.category.api.exception;

import app.openschool.common.response.ResponseMessage;
import java.util.Locale;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CategoryExceptionHandler implements ErrorController {

  private final MessageSource messageSource;

  public CategoryExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(IncorrectCategoryTitleException.class)
  public ResponseEntity<ResponseMessage> handleIncorrectCategoryTitleException(Locale locale) {
    String message = messageSource.getMessage("category.title.incorrect", null, locale);
    return ResponseEntity.badRequest().body(new ResponseMessage(message));
  }

  @ExceptionHandler(ImageNotExistsException.class)
  public ResponseEntity<ResponseMessage> handleImageNotExistsException(Locale locale) {
    String message = messageSource.getMessage("category.logo.blank", null, locale);
    return ResponseEntity.badRequest().body(new ResponseMessage(message));
  }
}
