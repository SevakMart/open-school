package app.openschool.common.communication.exceptionhandler;

import java.io.UnsupportedEncodingException;
import java.util.Locale;
import javax.mail.MessagingException;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class MailExceptionHandler implements ErrorController {

  private final MessageSource messageSource;

  public MailExceptionHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  @ExceptionHandler(MessagingException.class)
  public ResponseEntity<String> messagingException(Locale locale) {
    return ResponseEntity.internalServerError()
        .body(messageSource.getMessage("exception.messaging", null, locale));
  }

  @ExceptionHandler(UnsupportedEncodingException.class)
  public ResponseEntity<String> unsupportedEncodingException(Locale locale) {
    return ResponseEntity.internalServerError()
        .body(messageSource.getMessage("exception.messaging", null, locale));
  }
}
