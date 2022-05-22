package app.openschool.auth.api.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class EmailNotFoundException extends BadCredentialsException {

  public EmailNotFoundException(String message) {
    super(message);
  }
}
