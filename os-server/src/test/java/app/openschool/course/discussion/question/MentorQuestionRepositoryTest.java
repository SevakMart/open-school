package app.openschool.course.discussion.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.api.CourseGenerator;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.mentor.question.MentorQuestion;
import app.openschool.course.discussion.mentor.question.MentorQuestionRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.role.Role;
import app.openschool.user.role.RoleRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
public class MentorQuestionRepositoryTest {

  @Autowired MentorQuestionRepository mentorQuestionRepository;
  @Autowired CourseRepository courseRepository;
  @Autowired DifficultyRepository difficultyRepository;
  @Autowired LanguageRepository languageRepository;
  @Autowired CategoryRepository categoryRepository;
  @Autowired EnrolledCourseRepository enrolledCourseRepository;
  @Autowired UserRepository userRepository;
  @Autowired RoleRepository roleRepository;

  private MentorQuestion expectedQuestion;
  private User student;
  private EnrolledCourse enrolledCourse;

  @BeforeEach
  public void setup() {

    User mentor = new User();
    Role role = roleRepository.save(new Role(2, "MENTOR"));
    mentor.setRole(role);
    mentor.setName("Mentor");
    mentor.setPassword("password");
    mentor.setEmail("email@email.com");

    mentor = userRepository.save(mentor);

    Difficulty difficulty = new Difficulty();
    difficulty.setTitle("Medium");
    difficulty = difficultyRepository.save(difficulty);

    Language language = new Language();
    language.setTitle("English");
    language = languageRepository.save(language);

    Category category = new Category();
    category.setTitle("Java");
    category = categoryRepository.save(category);

    Course course = new Course();
    course.setTitle("Title");
    course.setMentor(mentor);
    course.setDifficulty(difficulty);
    course.setLanguage(language);
    course.setCategory(category);
    course = courseRepository.save(course);

    student = new User();
    role = roleRepository.save(new Role(1, "STUDENT"));
    student.setRole(role);
    student.setName("Student");
    student.setPassword("password");
    student.setEmail("student@email.com");
    student = userRepository.save(student);

    enrolledCourse = CourseGenerator.generateEnrolledCourse();
    enrolledCourse.setCourse(course);
    enrolledCourse.setUser(student);
    enrolledCourse = enrolledCourseRepository.save(enrolledCourse);

    expectedQuestion = TestHelper.createMentorQuestion();
    expectedQuestion.setCourse(course);
    expectedQuestion.setUser(student);
    expectedQuestion = mentorQuestionRepository.save(expectedQuestion);
  }

  @Test
  void findMentorQuestionsByEnrolledCourseId() {
    long courseId = enrolledCourse.getId();
    long wrongEnrolledCourseId = 999L;
    String searchQuery = "Question";
    String userEmail = "student@email.com";

    Page<MentorQuestion> questionPage =
        mentorQuestionRepository.findQuestionByEnrolledCourseId(
            courseId, userEmail, PageRequest.of(0, 1), searchQuery);
    Optional<MentorQuestion> questionOptional = questionPage.stream().findFirst();

    Page<MentorQuestion> emptyPage =
        mentorQuestionRepository.findQuestionByEnrolledCourseId(
            wrongEnrolledCourseId, userEmail, PageRequest.of(0, 1), searchQuery);

    assertTrue(questionOptional.isPresent(), "Expected mentor question to be present");
    assertEquals(expectedQuestion.getCourse().getId(), questionOptional.get().getCourse().getId());
    assertEquals(0, emptyPage.getTotalElements());
  }

  @Test
  void findQuestionByIdAndEnrolledCourseId() {

    long correctEnrolledCourseId = expectedQuestion.getCourse().getId();
    Optional<MentorQuestion> actualQuestion =
        mentorQuestionRepository.findQuestionByIdAndEnrolledCourseId(
            correctEnrolledCourseId, expectedQuestion.getId());

    long wrongEnrolledCourseId = 999L;
    Optional<MentorQuestion> emptyOptionalWrongEnrolledId =
        mentorQuestionRepository.findQuestionByIdAndEnrolledCourseId(
            wrongEnrolledCourseId, expectedQuestion.getId());

    long wrongQuestionId = 999L;
    Optional<MentorQuestion> emptyOptionalWrongQuestionId =
        mentorQuestionRepository.findQuestionByIdAndEnrolledCourseId(
            correctEnrolledCourseId, wrongQuestionId);

    assertEquals(expectedQuestion.getId(), actualQuestion.orElseThrow().getId());
    assertTrue(emptyOptionalWrongEnrolledId.isEmpty());
    assertTrue(emptyOptionalWrongQuestionId.isEmpty());
  }
}
