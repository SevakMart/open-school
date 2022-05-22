package app.openschool.common.event;

import app.openschool.user.User;
import org.springframework.context.ApplicationEvent;

public class SendVerificationEmailEvent extends ApplicationEvent {
  private User user;

  public SendVerificationEmailEvent(Object source, User user) {
    super(source);
    this.user = user;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
