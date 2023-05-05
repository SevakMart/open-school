package app.openschool.course.discussion;

import app.openschool.user.User;
import java.time.Instant;

public interface Answer {

  Long getId();

  void setId(Long id);

  String getText();

  void setText(String text);

  User getUser();

  void setUser(User user);

  Instant getCreatedDate();

  void setCreatedDate(Instant createdDate);
}
