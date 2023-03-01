package app.openschool.course.module.quiz;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.api.CourseGenerator;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.course.module.Module;
import app.openschool.course.module.ModuleRepository;
import app.openschool.course.module.api.ModuleGenerator;
import app.openschool.course.module.item.ModuleItemRepository;
import app.openschool.course.module.item.type.ModuleItemTypeRepository;
import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.question.QuestionRepository;
import app.openschool.course.module.quiz.question.answer.AnswerOption;
import app.openschool.course.module.quiz.question.type.QuestionType;
import app.openschool.course.module.quiz.question.util.QuestionGenerator;
import app.openschool.course.module.quiz.util.QuizGenerator;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.api.UserGenerator;
import app.openschool.user.company.CompanyRepository;
import app.openschool.user.role.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class QuizRepositoryTest {
  @Autowired QuizRepository quizRepository;
  @Autowired ModuleRepository moduleRepository;
  @Autowired UserRepository userRepository;
  @Autowired CourseRepository courseRepository;
  @Autowired CategoryRepository categoryRepository;
  @Autowired DifficultyRepository difficultyRepository;
  @Autowired LanguageRepository languageRepository;

  @Autowired QuestionRepository questionRepository;

  @BeforeEach
  public void setup() {
    User user = UserGenerator.generateMentor();
    userRepository.save(user);
    Category category = new Category();
    category.setTitle("category title");
    categoryRepository.save(category);
    Difficulty difficulty = new Difficulty();
    difficulty.setTitle("difficulty title");
    difficultyRepository.save(difficulty);
    Language language = new Language();
    language.setTitle("English");
    languageRepository.save(language);
    Course course = new Course();
    course.setTitle("Stream");
    course.setRating(5.5);
    course.setCategory(categoryRepository.getById(1L));
    course.setDifficulty(difficultyRepository.getById(1));
    course.setLanguage(languageRepository.getById(1));
    course.setMentor(user);
    courseRepository.save(course);
    Module module = ModuleGenerator.generateModule();
    module.setCourse(course);
    module.setDescription("module description");
    moduleRepository.save(module);
  }

  @Test
  void saveQuiz() {
    Quiz quiz = new Quiz();
    quiz.setTitle("quiz title");
    quiz.setDescription("quiz description");
    Question question = new Question();
    question.setQuestion("question");
    AnswerOption answerOption = new AnswerOption();
    answerOption.setAnswerOption("Answer1");
    answerOption.setRightAnswer(true);
    answerOption.setQuestion(question);
    Set<AnswerOption> answerOptions = new HashSet<>();
    answerOptions.add(answerOption);
    question.setAnswerOptions(answerOptions);
    question.setQuiz(quiz);
    QuestionType questionType = new QuestionType(1L, "Question type");
    question.setQuestionType(questionType);
    Set<Question> questions = new HashSet<>();
    questions.add(question);
    quiz.setQuestions(questions);
    quiz.setMaxGrade(7);
    quiz.setPassingScore(5);
    quiz.setModule(moduleRepository.getById(1L));
    Quiz savedQuiz = quizRepository.save(quiz);
    assertEquals(quiz.getTitle(), savedQuiz.getTitle());
    assertEquals(quiz.getDescription(), savedQuiz.getDescription());
    assertEquals(quiz.getMaxGrade(), savedQuiz.getMaxGrade());
    assertEquals(quiz.getPassingScore(), savedQuiz.getPassingScore());
    assertEquals(quiz.getQuestions(), savedQuiz.getQuestions());
    assertEquals(
        Objects.requireNonNull(quiz.getQuestions().stream().findFirst().orElse(null)).getAnswerOptions().stream().findFirst(),
        Objects.requireNonNull(savedQuiz.getQuestions().stream().findFirst().orElse(null)).getAnswerOptions().stream().findFirst());
    assertEquals(quiz.getModule(), savedQuiz.getModule());
  }
}
