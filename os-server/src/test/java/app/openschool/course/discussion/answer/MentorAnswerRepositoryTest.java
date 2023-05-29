package app.openschool.course.discussion.answer;

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
import app.openschool.course.discussion.mentor.answer.MentorAnswer;
import app.openschool.course.discussion.mentor.answer.MentorAnswerRepository;
import app.openschool.course.discussion.mentor.question.MentorQuestion;
import app.openschool.course.discussion.mentor.question.MentorQuestionRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.role.Role;
import app.openschool.user.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
class MentorAnswerRepositoryTest {
  @Autowired MentorAnswerRepository mentorAnswerRepository;
  @Autowired MentorQuestionRepository mentorQuestionRepository;
  @Autowired CourseRepository courseRepository;
  @Autowired DifficultyRepository difficultyRepository;
  @Autowired LanguageRepository languageRepository;
  @Autowired CategoryRepository categoryRepository;
  @Autowired EnrolledCourseRepository enrolledCourseRepository;
  @Autowired UserRepository userRepository;
  @Autowired RoleRepository roleRepository;

  private MentorAnswer expectedMentorAnswer;
  private MentorAnswer mentorAnswer;
  private MentorQuestion expectedQuestion;
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

    expectedQuestion = TestHelper.createMentorQuestion();
    expectedQuestion.setCourse(course);
    expectedQuestion.setUser(student);
    expectedQuestion = mentorQuestionRepository.save(expectedQuestion);

    mentorAnswer = TestHelper.createMentorAnswer();
    mentorAnswer.setDiscussionQuestionMentor(expectedQuestion);
    mentorAnswer.setUser(student);
    expectedMentorAnswer = mentorAnswerRepository.save(mentorAnswer);
  }

  @Test
  void findMentorAnswerByMentorQuestionId() {

    long correctQuestionId = expectedMentorAnswer.getDiscussionQuestionMentor().getId();
    long wrongQuestionId = 999L;

    Page<MentorAnswer> mentorAnswerPage =
        mentorAnswerRepository.findMentorAnswerByMentorQuestionId(
            correctQuestionId, PageRequest.of(0, 1));
    Optional<MentorAnswer> optionalAnswer = mentorAnswerPage.stream().findFirst();

    Page<MentorAnswer> emptyPage =
        mentorAnswerRepository.findMentorAnswerByMentorQuestionId(
            wrongQuestionId, PageRequest.of(0, 1));

    assertEquals(expectedMentorAnswer.getId(), optionalAnswer.orElseThrow().getId());
    assertEquals(0, emptyPage.getTotalElements());
  }

  @Test
  void delete() {

    MentorAnswer mentorAnswer =
        mentorAnswerRepository.findById(expectedMentorAnswer.getId()).orElseThrow();

    int updatedRows =
        mentorAnswerRepository.delete(
            mentorAnswer.getId(),
            mentorAnswer.getDiscussionQuestionMentor().getId(),
            enrolledCourse.getId(),
            student.getEmail());

    assertEquals(1, updatedRows);
    assertEquals(mentorAnswerRepository.findById(mentorAnswer.getId()), Optional.empty());
  }

  @Test
  void findMentorAnswerByIdAndUserEmailAndQuestionId() {
    MentorAnswer actualMentorAnswer =
        mentorAnswerRepository
            .findMentorAnswerByIdAndUserEmailAndQuestionId(
                expectedMentorAnswer.getId(),
                expectedMentorAnswer.getDiscussionQuestionMentor().getId(),
                enrolledCourse.getId(),
                student.getEmail())
            .orElseThrow(IllegalArgumentException::new);

    assertEquals(expectedMentorAnswer.getId(), actualMentorAnswer.getId());
    assertEquals(
        expectedMentorAnswer.getUser().getEmail(), actualMentorAnswer.getUser().getEmail());
    assertEquals(
        expectedMentorAnswer.getDiscussionQuestionMentor().getId(),
        actualMentorAnswer.getDiscussionQuestionMentor().getId());
  }
}
