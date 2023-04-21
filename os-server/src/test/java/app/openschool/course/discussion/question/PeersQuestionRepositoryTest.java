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
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.course.discussion.peers.question.PeersQuestionRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.api.UserGenerator;
import app.openschool.user.role.Role;
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

  private PeersQuestion expectedPeersQuestion;
  private User studentUser;
  private EnrolledCourse enrolledCourse;

  @BeforeEach
  public void setup() {
    expectedPeersQuestion = TestHelper.createDiscussionPeersQuestion();
    studentUser = expectedPeersQuestion.getUser();
    studentUser.setRole(new Role(1, "STUDENT"));

    userRepository.save(studentUser);
    final User mentor = userRepository.save(UserGenerator.generateUser());
    Difficulty difficulty = new Difficulty();
    difficulty.setTitle("Medium");
    difficultyRepository.save(difficulty);

    Language language = new Language();
    language.setTitle("English");
    languageRepository.save(language);

    Category category = new Category();
    category.setTitle("Java");
    categoryRepository.save(category);

    Course course = expectedPeersQuestion.getCourse();

    course.setCategory(category);
    course.setLanguage(language);
    course.setDifficulty(difficulty);
    course.setMentor(mentor);

    courseRepository.save(course);
    enrolledCourse = CourseMapper.toEnrolledCourse(course, studentUser);
    enrolledCourseRepository.save(enrolledCourse);
    peersQuestionRepository.save(expectedPeersQuestion);
  }

  @Test
  void findPeersQuestionByIdAndUserEmailAndEnrolledCourseId() {

    PeersQuestion actualPeersQuestion =
        peersQuestionRepository
            .findPeersQuestionByIdAndUserEmailAndEnrolledCourseId(
                expectedPeersQuestion.getId(), studentUser.getEmail(), enrolledCourse.getId())
            .orElseThrow(IllegalArgumentException::new);

    assertEquals(expectedPeersQuestion.getId(), actualPeersQuestion.getId());
    assertEquals(
        expectedPeersQuestion.getUser().getEmail(), actualPeersQuestion.getUser().getEmail());
    assertEquals(
        expectedPeersQuestion.getCourse().getId(), actualPeersQuestion.getCourse().getId());
  }

  @Test
  void findQuestionByCourseId() {
    long courseId = expectedPeersQuestion.getCourse().getId();
    long wrongEnrolledCourseId = 999L;

    Page<PeersQuestion> questionPage =
        peersQuestionRepository.findQuestionByCourseId(courseId, PageRequest.of(0, 1));
    // Optional<PeersQuestion> questionOptional = questionPage.stream().findFirst();

    Page<PeersQuestion> emptyPage =
        peersQuestionRepository.findQuestionByCourseId(wrongEnrolledCourseId, PageRequest.of(0, 1));

    //    assertEquals(
    //        expectedPeersQuestion.getCourse().getId(),
    //        questionOptional.orElseThrow().getCourse().getId());
    assertEquals(0, emptyPage.getTotalElements());
  }

  @Test
  void findQuestionByIdAndEnrolledCourseId() {

    long correctEnrolledCourseId = expectedPeersQuestion.getCourse().getId();
    Optional<PeersQuestion> actualQuestion =
        peersQuestionRepository.findQuestionByIdAndEnrolledCourseId(
            correctEnrolledCourseId, expectedPeersQuestion.getId());

    long wrongEnrolledCourseId = 999L;
    Optional<PeersQuestion> emptyOptionalWrongEnrolledId =
        peersQuestionRepository.findQuestionByIdAndEnrolledCourseId(
            wrongEnrolledCourseId, expectedPeersQuestion.getId());

    long wrongQuestionId = 999L;
    Optional<PeersQuestion> emptyOptionalWrongQuestionId =
        peersQuestionRepository.findQuestionByIdAndEnrolledCourseId(
            correctEnrolledCourseId, wrongQuestionId);

    assertEquals(expectedPeersQuestion.getId(), actualQuestion.orElseThrow().getId());
    assertTrue(emptyOptionalWrongEnrolledId.isEmpty());
    assertTrue(emptyOptionalWrongQuestionId.isEmpty());
  }

  @Test
  void delete() {

    PeersQuestion peersQuestion =
        peersQuestionRepository.findById(expectedPeersQuestion.getId()).orElseThrow();

    int updatedRows =
        peersQuestionRepository.delete(
            peersQuestion.getId(), studentUser.getEmail(), enrolledCourse.getId());

    assertEquals(1, updatedRows);
    assertEquals(peersQuestionRepository.findById(peersQuestion.getId()), Optional.empty());
  }
}
