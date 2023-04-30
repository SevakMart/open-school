package app.openschool.course.discussion.dto.basedto;

import app.openschool.course.Course;
import app.openschool.user.User;
import java.time.Instant;

public interface IQuestion {

  Long getId();

  void setId(Long id);

  String getText();

  void setText(String text);

  User getUser();

  void setUser(User user);

  Course getCourse();

  void setCourse(Course course);

  Instant getCreatedDate();

  void setCreatedDate(Instant createdDate);
}
