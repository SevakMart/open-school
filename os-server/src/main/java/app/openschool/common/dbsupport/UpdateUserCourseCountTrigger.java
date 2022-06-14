package app.openschool.common.dbsupport;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.h2.api.Trigger;

public class UpdateUserCourseCountTrigger implements Trigger {

  @Override
  public void init(Connection connection, String s, String s1, String s2, boolean b, int i) {}

  @Override
  public void fire(Connection connection, Object[] objects, Object[] objects1) {
    try (PreparedStatement statement =
        connection.prepareStatement(
            "UPDATE user u SET u.course_count =\n"
                + "(SELECT COUNT(*) FROM learning_path lp\n"
                + "WHERE lp.mentor_id = u.id);")) {
      statement.executeUpdate();
    } catch (SQLException exception) {
      exception.printStackTrace();
    }
  }

  @Override
  public void close() {}

  @Override
  public void remove() {}
}
