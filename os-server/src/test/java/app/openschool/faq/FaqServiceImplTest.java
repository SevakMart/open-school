package app.openschool.faq;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.faq.api.dto.CreateFaqRequest;
import app.openschool.faq.api.dto.UpdateFaqDtoRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
@ExtendWith(MockitoExtension.class)
@Sql(scripts = {"/insertData.sql"})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class FaqServiceImplTest {

  @Autowired FaqRepository faqRepository;
  @Autowired CourseRepository courseRepository;

  private FaqServiceImpl faqService;

  @BeforeEach
  void setup() {
    faqService = new FaqServiceImpl(faqRepository, courseRepository);
  }

  @Test
  void add_withCorrectData() throws SQLIntegrityConstraintViolationException {
    // given
    Course course = courseRepository.findById(1L).orElseThrow(IllegalArgumentException::new);

    // there is a mentor in the database with this email address
    final String mentorEmail = "poxos@gm.com";
    CreateFaqRequest request = new CreateFaqRequest();
    request.setQuestion("add");
    request.setAnswer("add");
    request.setCourseId(course.getId());

    Faq expectedFaq = new Faq();
    expectedFaq.setQuestion(request.getQuestion());
    expectedFaq.setAnswer(request.getAnswer());
    expectedFaq.setCourse(course);

    // when
    Faq actualFaq = faqService.add(request, mentorEmail);

    // then
    assertEquals(expectedFaq.getQuestion(), actualFaq.getQuestion());
    assertEquals(expectedFaq.getAnswer(), actualFaq.getAnswer());
    assertEquals(expectedFaq.getCourse().getId(), actualFaq.getCourse().getId());
  }

  @Test
  void add_withIncorrectData() {

    Course course = courseRepository.findById(1L).orElseThrow(IllegalArgumentException::new);

    // there is no user with such an email in the database
    String wrongMentorEmail = "wrong@gm.com";
    CreateFaqRequest request = new CreateFaqRequest("add", "add", course.getId());

    assertThatThrownBy(() -> faqService.add(request, wrongMentorEmail))
        .isInstanceOf(DataIntegrityViolationException.class);
  }

  @Test
  void add_withIncorrectCourseId() {

    long wrongCourseId = 999L;

    // there is no user with such an email in the database
    String mentorEmail = "poxos@gm.com";
    CreateFaqRequest request = new CreateFaqRequest("add", "add", wrongCourseId);

    assertThatThrownBy(() -> faqService.add(request, mentorEmail))
        .isInstanceOf(DataIntegrityViolationException.class);
  }

  @Test
  void update_withCorrectData() {

    // given
    long faqId = faqRepository.findById(1L).orElseThrow().getId();

    // there is a mentor in the database with this email address
    String mentorEmail = "poxos@gm.com";

    UpdateFaqDtoRequest updateRequest = new UpdateFaqDtoRequest();
    updateRequest.setQuestion("update question");
    updateRequest.setAnswer("update answer");
    // when
    Faq updatedFaq = faqService.update(updateRequest, faqId, mentorEmail);
    // then
    assertEquals(updateRequest.getQuestion(), updatedFaq.getQuestion());
    assertEquals(updateRequest.getAnswer(), updatedFaq.getAnswer());
    assertEquals(faqId, updatedFaq.getId());
  }

  @Test
  void update_withIncorrectFaqId() {

    long wrongFaqId = 999L;

    // there is a mentor in the database with this email address
    String mentorEmail = "poxos@gm.com";

    UpdateFaqDtoRequest updateRequest = new UpdateFaqDtoRequest("update question", "update answer");

    assertThatThrownBy(() -> faqService.update(updateRequest, wrongFaqId, mentorEmail))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void update_withIncorrectEmail() {

    long faqId = faqRepository.findById(1L).orElseThrow().getId();

    // there is no mentor in the database with this email address
    String wrongEmail = "wrong@gm.com";

    UpdateFaqDtoRequest updateRequest = new UpdateFaqDtoRequest("update question", "update answer");

    assertThatThrownBy(() -> faqService.update(updateRequest, faqId, wrongEmail))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void delete_withCorrectData() {

    // given
    Faq toDelete = faqRepository.findById(1L).orElseThrow();
    String mentorEmail = "poxos@gm.com";
    // when
    faqService.delete(toDelete.getId(), mentorEmail);
    // then
    assertTrue(faqRepository.findById(toDelete.getId()).isEmpty());
  }

  @Test
  void delete_withIncorrectEmail() {

    Faq toDelete = faqRepository.findById(1L).orElseThrow();
    String wrongEmail = "wrong@gm.com";
    assertThatThrownBy(() -> faqService.delete(toDelete.getId(), wrongEmail))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void delete_withIncorrectFaqId() {

    long wrongId = 999L;
    String mentorEmail = "poxos@gm.com";
    assertThatThrownBy(() -> faqService.delete(wrongId, mentorEmail))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void findFaqsByCourseId_withCorrectCourseId() {

    // given
    long courseId = courseRepository.findById(1L).orElseThrow().getId();

    Pageable pageable = PageRequest.of(0, 2);
    // when
    Page<Faq> faqsByCourseId = faqService.findFaqsByCourseId(courseId, pageable);
    // then
    assertTrue(
        faqsByCourseId.stream()
            .allMatch(content -> Objects.equals(content.getCourse().getId(), courseId)));
  }

  @Test
  void findFaqsByCourseId_withIncorrectCourseId() {

    long wrongCourseId = 999L;
    Pageable pageable = PageRequest.of(0, 2);

    assertThatThrownBy(() -> faqService.findFaqsByCourseId(wrongCourseId, pageable))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void findAllFaqs() {
    Pageable pageable = PageRequest.of(0, 2);
    Page<Faq> all = faqService.findAll(pageable);
    assertTrue(all.getTotalElements() != 0);
  }
}
