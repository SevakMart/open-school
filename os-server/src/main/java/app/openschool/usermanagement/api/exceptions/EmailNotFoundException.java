package app.openschool.usermanagement.api.exceptions;

import org.springframework.security.authentication.BadCredentialsException;

public class EmailNotFoundException extends BadCredentialsException {

  public EmailNotFoundException (String message) {
    super(message);
  }
}
