package app.openschool.course.discussion;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.discussion.peers.answer.PeersAnswer;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.user.User;
import java.security.Principal;
import java.time.Instant;
import org.apache.http.auth.BasicUserPrincipal;

public class TestHelper {
  public static Principal createPrincipal() {
    return new BasicUserPrincipal("user@email");
  }

  public static PeersQuestion createDiscussionPeersQuestion() {
    PeersQuestion peersQuestion = new PeersQuestion();
    peersQuestion.setId(1L);
    peersQuestion.setText("Question.");
    peersQuestion.setCourse(crateCourse());
    peersQuestion.setUser(createUser());
    peersQuestion.setCreatedDate(Instant.now());
    return peersQuestion;
  }

  public static PeersAnswer createDiscussionPeersAnswer() {
    PeersAnswer answer = new PeersAnswer();
    answer.setId(1L);
    answer.setText("Answer");
    answer.setUser(createUser());
    answer.setDiscussionQuestion(createDiscussionPeersQuestion());
    answer.setCreatedDate(Instant.now());
    return answer;
  }

  public static User createUser() {
    User user = new User();
    user.setId(1L);
    user.setName("John");
    user.setEmail("user@email");
    user.setPassword("Password*!1");
    return user;
  }

  public static Course crateCourse() {
    Course course = new Course();
    course.setId(1L);
    course.setTitle("title");
    Difficulty difficulty = new Difficulty("Difficulty title");
    course.setDifficulty(difficulty);
    course.setRating(1d);
    //    Set<Keyword> keywords = new HashSet<>();
    //    keywords.add(new Keyword(1L));
    //    keywords.add(new Keyword(1L));
    //    course.setKeywords(keywords);
    return course;
  }

  public static EnrolledCourse createEnrolledCourse(User user, Course course) {
    EnrolledCourse enrolledCourse = new EnrolledCourse();
    enrolledCourse.setId(1L);
    enrolledCourse.setUser(user);
    enrolledCourse.setCourse(course);
    return enrolledCourse;
  }
}
