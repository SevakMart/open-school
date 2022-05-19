package app.openschool.common.event;

import org.springframework.context.ApplicationEvent;

public class SendResetPasswordEmailEvent extends ApplicationEvent {
  String email;
  String token;

  public SendResetPasswordEmailEvent(Object source, String email, String token) {
    super(source);
    this.email = email;
    this.token = token;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
