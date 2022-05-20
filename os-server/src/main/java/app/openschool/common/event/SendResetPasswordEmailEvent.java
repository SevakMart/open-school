package app.openschool.common.event;

import org.springframework.context.ApplicationEvent;

public class SendResetPasswordEmailEvent extends ApplicationEvent {

  private final String email;
  private final String token;

  public SendResetPasswordEmailEvent(Object source, String email, String token) {
    super(source);
    this.email = email;
    this.token = token;
  }

  public String getEmail() {
    return email;
  }

  public String getToken() {
    return token;
  }
}
