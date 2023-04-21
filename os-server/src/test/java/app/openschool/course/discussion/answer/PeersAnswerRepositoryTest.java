package app.openschool.course.discussion.answer;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import app.openschool.course.discussion.peers.answer.PeersAnswer;
import app.openschool.course.discussion.peers.answer.PeersAnswerRepository;
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

@DataJpaTest
public class PeersAnswerRepositoryTest {

  @Autowired PeersAnswerRepository peersAnswerRepository;
  @Autowired PeersQuestionRepository peersQuestionRepository;
  @Autowired CourseRepository courseRepository;
  @Autowired DifficultyRepository difficultyRepository;
  @Autowired LanguageRepository languageRepository;
  @Autowired CategoryRepository categoryRepository;
  @Autowired EnrolledCourseRepository enrolledCourseRepository;
  @Autowired UserRepository userRepository;

  private PeersAnswer expectedPeersAnswer;
  private PeersQuestion peersQuestion;
  private EnrolledCourse enrolledCourse;

  @BeforeEach
  void setup() {
    expectedPeersAnswer = TestHelper.createDiscussionPeersAnswer();

    peersQuestion = expectedPeersAnswer.getDiscussionQuestion();

    User studentUser = peersQuestion.getUser();
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

    Course course = peersQuestion.getCourse();

    course.setCategory(category);
    course.setLanguage(language);
    course.setDifficulty(difficulty);
    course.setMentor(mentor);

    courseRepository.save(course);
    enrolledCourse = CourseMapper.toEnrolledCourse(course, studentUser);
    enrolledCourseRepository.save(enrolledCourse);
    peersQuestionRepository.save(peersQuestion);
    peersAnswerRepository.save(expectedPeersAnswer);
  }

  @Test
  void findPeersAnswerByPeersQuestionId() {

    long correctQuestionId = expectedPeersAnswer.getDiscussionQuestion().getId();
    long wrongQuestionId = 999L;

    Page<PeersAnswer> peersAnswerPage =
        peersAnswerRepository.findPeersAnswerByPeersQuestionId(
            correctQuestionId, PageRequest.of(0, 1));
    Optional<PeersAnswer> optionalAnswer = peersAnswerPage.stream().findFirst();

    Page<PeersAnswer> emptyPage =
        peersAnswerRepository.findPeersAnswerByPeersQuestionId(
            wrongQuestionId, PageRequest.of(0, 1));

    assertEquals(expectedPeersAnswer.getId(), optionalAnswer.orElseThrow().getId());
    assertEquals(0, emptyPage.getTotalElements());
  }
}
