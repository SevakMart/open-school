package app.openschool.course.discussion;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.course.keyword.Keyword;
import app.openschool.user.User;
import java.security.Principal;
import java.util.HashSet;
import java.util.Set;
import org.apache.http.auth.BasicUserPrincipal;

public class TestHelper {
  static Principal createPrincipal() {
    return new BasicUserPrincipal("user@email");
  }

  static PeersQuestion createDiscussionQuestion() {
    PeersQuestion peersQuestion = new PeersQuestion();
    peersQuestion.setId(1L);
    peersQuestion.setText("Question.");
    peersQuestion.setCourse(crateCourse());
    peersQuestion.setUser(createUser());
    return peersQuestion;
  }

  static User createUser() {
    User user = new User();
    user.setId(1L);
    user.setName("John");
    user.setEmail("user@email");
    return user;
  }

  static Course crateCourse() {
    Course course = new Course();
    course.setId(1L);
    course.setTitle("title");
    Difficulty difficulty = new Difficulty("Difficulty title");
    course.setDifficulty(difficulty);
    course.setRating(1d);
    Set<Keyword> keywords = new HashSet<>();
    keywords.add(new Keyword(1L));
    keywords.add(new Keyword(1L));
    course.setKeywords(keywords);
    return course;
  }

  static EnrolledCourse createEnrolledCourse(User user, Course course) {
    EnrolledCourse enrolledCourse = new EnrolledCourse();
    enrolledCourse.setId(1L);
    enrolledCourse.setUser(user);
    enrolledCourse.setCourse(course);
    return enrolledCourse;
  }
}
