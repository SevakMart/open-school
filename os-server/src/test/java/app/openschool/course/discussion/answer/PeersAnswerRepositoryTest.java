package app.openschool.course.discussion.answer;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import app.openschool.course.discussion.peers.answer.PeersAnswer;
import app.openschool.course.discussion.peers.answer.PeersAnswerRepository;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.course.discussion.peers.question.PeersQuestionRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.role.Role;
import java.util.Optional;

import app.openschool.user.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@DataJpaTest
class PeersAnswerRepositoryTest {

  @Autowired PeersAnswerRepository peersAnswerRepository;
  @Autowired PeersQuestionRepository peersQuestionRepository;
  @Autowired CourseRepository courseRepository;
  @Autowired DifficultyRepository difficultyRepository;
  @Autowired LanguageRepository languageRepository;
  @Autowired CategoryRepository categoryRepository;
  @Autowired EnrolledCourseRepository enrolledCourseRepository;
  @Autowired UserRepository userRepository;

  @Autowired RoleRepository roleRepository;

  private PeersAnswer expectedPeersAnswer;
  private PeersQuestion expectedQuestion;
  private EnrolledCourse enrolledCourse;

  private User student;

  @BeforeEach
  void setup() {

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

    expectedPeersAnswer = TestHelper.createDiscussionPeersAnswer();
    expectedPeersAnswer.setDiscussionQuestion(expectedQuestion);
    expectedPeersAnswer.setUser(student);
    expectedPeersAnswer = peersAnswerRepository.save(expectedPeersAnswer);
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

  @Test
  void delete() {

    PeersAnswer peersAnswer =
        peersAnswerRepository.findById(expectedPeersAnswer.getId()).orElseThrow();

    int updatedRows =
        peersAnswerRepository.delete(
            peersAnswer.getId(),
            peersAnswer.getDiscussionQuestion().getId(),
            enrolledCourse.getId(),
            student.getEmail());

    assertEquals(1, updatedRows);
    assertEquals(peersAnswerRepository.findById(peersAnswer.getId()), Optional.empty());
  }

  @Test
  void findPeersAnswerByIdAndUserEmailAndQuestionId() {
    PeersAnswer actualPeersAnswer =
        peersAnswerRepository
            .findPeersAnswerByIdAndUserEmailAndQuestionId(
                expectedPeersAnswer.getId(),
                expectedPeersAnswer.getDiscussionQuestion().getId(),
                enrolledCourse.getId(),
                student.getEmail())
            .orElseThrow(IllegalArgumentException::new);

    assertEquals(expectedPeersAnswer.getId(), actualPeersAnswer.getId());
    assertEquals(expectedPeersAnswer.getUser().getEmail(), actualPeersAnswer.getUser().getEmail());
    assertEquals(
        expectedPeersAnswer.getDiscussionQuestion().getId(),
        actualPeersAnswer.getDiscussionQuestion().getId());
  }
}
