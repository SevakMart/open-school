package app.openschool.course.discussion;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.mentor.answer.MentorAnswer;
import app.openschool.course.discussion.mentor.question.MentorQuestion;
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

  public static MentorQuestion createMentorQuestion() {
    MentorQuestion mentorQuestion = new MentorQuestion();
    mentorQuestion.setId(1L);
    mentorQuestion.setText("Question to Mentor");
    mentorQuestion.setUser(createUser());
    mentorQuestion.setCourse(crateCourse());
    mentorQuestion.setCreatedDate(Instant.now());
    return mentorQuestion;
  }
  public static MentorAnswer createMentorAnswer() {
    MentorAnswer mentorAnswer = new MentorAnswer();
    mentorAnswer.setId(1L);
    mentorAnswer.setText("Question to Mentor");
    mentorAnswer.setUser(createUser());
    mentorAnswer.setDiscussionQuestionMentor(createMentorQuestion());
    mentorAnswer.setCreatedDate(Instant.now());
    return mentorAnswer;
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
    return course;
  }

  public static EnrolledCourse createEnrolledCourse(User user, Course course) {
    EnrolledCourse enrolledCourse = new EnrolledCourse();
    enrolledCourse.setId(1L);
    enrolledCourse.setUser(user);
    enrolledCourse.setCourse(course);
    return enrolledCourse;
  }

  public static QuestionRequestDto crateDiscussionQuestionRequestDto() {
    QuestionRequestDto requestDto = new QuestionRequestDto();
    requestDto.setText("text");
    return requestDto;
  }
}
