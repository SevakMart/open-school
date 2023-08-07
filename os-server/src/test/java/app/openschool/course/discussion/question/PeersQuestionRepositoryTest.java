package app.openschool.course.discussion.question;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

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
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.course.discussion.peers.question.PeersQuestionRepository;
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
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class PeersQuestionRepositoryTest {

  @Autowired PeersQuestionRepository peersQuestionRepository;
  @Autowired CourseRepository courseRepository;
  @Autowired DifficultyRepository difficultyRepository;
  @Autowired LanguageRepository languageRepository;
  @Autowired CategoryRepository categoryRepository;
  @Autowired EnrolledCourseRepository enrolledCourseRepository;
  @Autowired UserRepository userRepository;
  @Autowired RoleRepository roleRepository;

  private PeersQuestion expectedQuestion;
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

    expectedQuestion = TestHelper.createDiscussionPeersQuestion();
    expectedQuestion.setCourse(course);
    expectedQuestion.setUser(student);
    expectedQuestion = peersQuestionRepository.save(expectedQuestion);
  }

  @Test
  void findPeersQuestionByIdAndUserEmailAndEnrolledCourseId() {

    PeersQuestion actualPeersQuestion =
        peersQuestionRepository
            .findPeersQuestionByIdAndUserEmailAndEnrolledCourseId(
                expectedQuestion.getId(), student.getEmail(), enrolledCourse.getId())
            .orElseThrow(IllegalArgumentException::new);

    assertEquals(expectedQuestion.getId(), actualPeersQuestion.getId());
    assertEquals(expectedQuestion.getUser().getEmail(), actualPeersQuestion.getUser().getEmail());
    assertEquals(expectedQuestion.getCourse().getId(), actualPeersQuestion.getCourse().getId());
  }

  @Test
  void findQuestionByCourseId() {
    long courseId = enrolledCourse.getId();
    long wrongEnrolledCourseId = 999L;
    String searchQuery = "Question";
    String userEmail = "student@email.com";

    Page<PeersQuestion> questionPage =
        peersQuestionRepository.findQuestionByEnrolledCourseId(
            courseId, userEmail, PageRequest.of(0, 1), searchQuery);
    Optional<PeersQuestion> questionOptional = questionPage.stream().findFirst();

    Page<PeersQuestion> emptyPage =
        peersQuestionRepository.findQuestionByEnrolledCourseId(
            wrongEnrolledCourseId, userEmail, PageRequest.of(0, 1), searchQuery);

    assertEquals(
        expectedQuestion.getCourse().getId(), questionOptional.orElseThrow().getCourse().getId());
    assertEquals(0, emptyPage.getTotalElements());
  }

  @Test
  void findQuestionByIdAndEnrolledCourseId() {

    long correctEnrolledCourseId = expectedQuestion.getCourse().getId();
    Optional<PeersQuestion> actualQuestion =
        peersQuestionRepository.findQuestionByIdAndEnrolledCourseId(
            correctEnrolledCourseId, expectedQuestion.getId());

    long wrongEnrolledCourseId = 999L;
    Optional<PeersQuestion> emptyOptionalWrongEnrolledId =
        peersQuestionRepository.findQuestionByIdAndEnrolledCourseId(
            wrongEnrolledCourseId, expectedQuestion.getId());

    long wrongQuestionId = 999L;
    Optional<PeersQuestion> emptyOptionalWrongQuestionId =
        peersQuestionRepository.findQuestionByIdAndEnrolledCourseId(
            correctEnrolledCourseId, wrongQuestionId);

    assertEquals(expectedQuestion.getId(), actualQuestion.orElseThrow().getId());
    assertTrue(emptyOptionalWrongEnrolledId.isEmpty());
    assertTrue(emptyOptionalWrongQuestionId.isEmpty());
  }

  @Test
  void delete() {

    PeersQuestion peersQuestion =
        peersQuestionRepository.findById(expectedQuestion.getId()).orElseThrow();

    int updatedRows =
        peersQuestionRepository.delete(
            peersQuestion.getId(), student.getEmail(), enrolledCourse.getId());

    assertEquals(1, updatedRows);
    assertEquals(peersQuestionRepository.findById(peersQuestion.getId()), Optional.empty());
  }
}
