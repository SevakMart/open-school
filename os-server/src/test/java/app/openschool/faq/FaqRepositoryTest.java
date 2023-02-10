package app.openschool.faq;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.faq.api.dto.CreateFaqRequest;
import app.openschool.faq.api.dto.UpdateFaqDtoRequest;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.role.Role;
import app.openschool.user.role.RoleRepository;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class FaqRepositoryTest {

  @Autowired FaqRepository faqRepository;
  @Autowired UserRepository userRepository;
  @Autowired CourseRepository courseRepository;
  @Autowired RoleRepository roleRepository;
  @Autowired DifficultyRepository difficultyRepository;
  @Autowired CategoryRepository categoryRepository;
  @Autowired LanguageRepository languageRepository;

  @Test
  void saveFaq() {

    Course saveCourse = getSavedInDbCourse(1L);

    Faq faq = new Faq();
    faq.setQuestion("question");
    faq.setAnswer("answer");
    faq.setCourse(saveCourse);

    CreateFaqRequest request = new CreateFaqRequest();
    request.setCourseId(saveCourse.getId());
    request.setQuestion(faq.getQuestion());
    request.setAnswer(faq.getAnswer());

    int updatedRows = faqRepository.saveFaq(request, saveCourse.getMentor().getEmail());
    assertEquals(1, updatedRows);
  }

  @Test
  void getLastInsertData() {

    Course saveCourse = getSavedInDbCourse(1L);

    Faq faq = new Faq();
    faq.setQuestion("question");
    faq.setAnswer("answer");
    faq.setCourse(saveCourse);

    CreateFaqRequest request = new CreateFaqRequest();
    request.setCourseId(saveCourse.getId());
    request.setQuestion(faq.getQuestion());
    request.setAnswer(faq.getAnswer());

    faqRepository.saveFaq(request, saveCourse.getMentor().getEmail());
    Faq lastInsertFaq = faqRepository.getLastInsertData().orElseThrow();

    assertEquals(faq.getQuestion(), lastInsertFaq.getQuestion());
    assertEquals(faq.getAnswer(), lastInsertFaq.getAnswer());
    assertEquals(faq.getCourse().getId(), lastInsertFaq.getCourse().getId());
  }

  @Test
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  void deleteFaq() throws SQLIntegrityConstraintViolationException {

    Faq createFaq = getSavedInDbFaq(getSavedInDbCourse(1L));
    int updatedRows =
        faqRepository.deleteFaq(createFaq.getId(), createFaq.getCourse().getMentor().getEmail());
    Optional<Faq> byId = faqRepository.findById(createFaq.getId());

    assertTrue(byId.isEmpty());
    assertEquals(1, updatedRows);
  }

  @Test
  @Transactional(propagation = Propagation.NOT_SUPPORTED)
  void updateFaq() throws SQLIntegrityConstraintViolationException {

    Faq faq = getSavedInDbFaq(getSavedInDbCourse(1L));
    UpdateFaqDtoRequest updateFaqDtoRequest =
        new UpdateFaqDtoRequest("updateQuestion", "updateAnswer");
    int updatedRows =
        faqRepository.updateFaq(
            updateFaqDtoRequest, faq.getId(), faq.getCourse().getMentor().getEmail());
    Faq updatedFaq = faqRepository.findById(faq.getId()).orElseThrow(IllegalArgumentException::new);

    assertEquals(1, updatedRows);
    assertEquals(updateFaqDtoRequest.getQuestion(), updatedFaq.getQuestion());
    assertEquals(updateFaqDtoRequest.getAnswer(), updatedFaq.getAnswer());
  }

  @Test
  @Transactional(propagation = Propagation.SUPPORTS)
  void findFaqsByCourseId() throws SQLIntegrityConstraintViolationException {

    Course firstCourse = getSavedInDbCourse(1L);
    Course secondCourse = getSavedInDbCourse(2L);
    getSavedInDbFaq(firstCourse);
    getSavedInDbFaq(firstCourse);
    getSavedInDbFaq(secondCourse);

    Pageable pageable = PageRequest.of(0, 2);
    Page<Faq> faqsByCourseId = faqRepository.findFaqsByCourseId(firstCourse.getId(), pageable);

    boolean isCorrectContent =
        faqsByCourseId.stream()
            .allMatch(content -> Objects.equals(content.getCourse().getId(), firstCourse.getId()));
    assertTrue(isCorrectContent);
  }

  private Course getSavedInDbCourse(long courseId) {

    Role saveRole = roleRepository.save(new Role("Mentor"));

    String uniqueEmail = System.currentTimeMillis() + "@gm.com";
    User saveMentor = userRepository.save(new User("Poxos", uniqueEmail, "Test#777", saveRole));

    Difficulty difficulty = difficultyRepository.save(new Difficulty("Medium"));

    String categoryTitle = System.currentTimeMillis() + "Java";
    Category category = categoryRepository.save(new Category(categoryTitle, null));

    Language language = languageRepository.save(new Language("English"));

    Course course = new Course(courseId, "Java Core");
    course.setMentor(saveMentor);
    course.setDifficulty(difficulty);
    course.setCategory(category);
    course.setLanguage(language);

    return courseRepository.save(course);
  }

  private Faq getSavedInDbFaq(Course course) throws SQLIntegrityConstraintViolationException {
    CreateFaqRequest request = new CreateFaqRequest();
    request.setCourseId(course.getId());
    request.setQuestion("question");
    request.setAnswer("answer");

    faqRepository.saveFaq(request, course.getMentor().getEmail());
    return faqRepository
        .getLastInsertData()
        .orElseThrow(SQLIntegrityConstraintViolationException::new);
  }
}
