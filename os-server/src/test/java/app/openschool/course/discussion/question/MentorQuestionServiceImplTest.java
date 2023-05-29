package app.openschool.course.discussion.question;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.api.CourseGenerator;
import app.openschool.course.discussion.Question;
import app.openschool.course.discussion.QuestionService;
import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import app.openschool.course.discussion.mentor.question.MentorQuestion;
import app.openschool.course.discussion.mentor.question.MentorQuestionRepository;
import app.openschool.course.discussion.mentor.question.MentorQuestionServiceImpl;
import app.openschool.user.User;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class MentorQuestionServiceImplTest {

  @Mock MentorQuestionRepository mentorQuestionRepository;
  @Mock EnrolledCourseRepository enrolledCourseRepository;
  private QuestionService questionService;

  @BeforeEach
  void setUp() {
    questionService =
        new MentorQuestionServiceImpl(enrolledCourseRepository, mentorQuestionRepository);
  }

  @Test
  void addMentorQuestion_withCorrectArguments_returnsCreatedQuestion() {
    MentorQuestion peersQuestion = TestHelper.createMentorQuestion();
    given(mentorQuestionRepository.save(any())).willReturn(peersQuestion);
    User user = TestHelper.createUser();
    Course course = TestHelper.crateCourse();
    EnrolledCourse enrolledCourse = TestHelper.createEnrolledCourse(user, course);
    doReturn(Optional.of(enrolledCourse)).when(enrolledCourseRepository).findById(anyLong());
    Question actual =
        questionService.create(
            enrolledCourse.getId(),
            TestHelper.crateDiscussionQuestionRequestDto(),
            TestHelper.createPrincipal().getName());

    assertTrue(actual instanceof MentorQuestion);
    assertEquals(peersQuestion.getId(), actual.getId());
    assertEquals(peersQuestion.getText(), actual.getText());
    assertEquals(peersQuestion.getUser().getId(), actual.getUser().getId());
    assertEquals(peersQuestion.getUser().getName(), actual.getUser().getName());
    assertEquals(peersQuestion.getUser().getEmail(), actual.getUser().getEmail());
    assertEquals(peersQuestion.getCourse().getId(), actual.getCourse().getId());
    assertEquals(peersQuestion.getCourse().getTitle(), actual.getCourse().getTitle());
    verify(mentorQuestionRepository, times(1)).save(any());
  }

  @Test
  void addQuestion_withInCorrectEnrolledCourseId() {

    String email = TestHelper.createPrincipal().getName();
    QuestionRequestDto requestDto = TestHelper.crateDiscussionQuestionRequestDto();
    long wrongEnrolledCourseId = 999L;

    given(enrolledCourseRepository.findById(wrongEnrolledCourseId)).willReturn(Optional.empty());

    assertThatThrownBy(() -> questionService.create(wrongEnrolledCourseId, requestDto, email))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void addQuestion_catchPermissionDeniedException() {

    EnrolledCourse enrolledCourse = new EnrolledCourse();
    enrolledCourse.setUser(new User(2L));
    long enrolledCourseId = 1L;
    QuestionRequestDto requestDto = TestHelper.crateDiscussionQuestionRequestDto();
    String email = TestHelper.createPrincipal().getName();

    doReturn(Optional.of(enrolledCourse)).when(enrolledCourseRepository).findById(enrolledCourseId);

    assertThatThrownBy(() -> questionService.create(enrolledCourseId, requestDto, email))
        .isInstanceOf(PermissionDeniedException.class);
  }

  @Test
  void update_withCorrectData() {

    MentorQuestion mentorQuestion = TestHelper.createMentorQuestion();

    UpdateQuestionRequest request = new UpdateQuestionRequest();
    String updateText = "Question was updated";
    request.setText(updateText);

    given(
            mentorQuestionRepository.findMentorQuestionByIdAndUserEmailAndEnrolledCourseId(
                anyLong(), anyString(), anyLong()))
        .willReturn(Optional.of(mentorQuestion));
    given(mentorQuestionRepository.save(any())).willReturn(mentorQuestion);

    mentorQuestion.setText(request.getText());

    Question updatedQuestion =
        questionService.update(
            request, mentorQuestion.getId(), 1L, mentorQuestion.getUser().getEmail());
    assertTrue(updatedQuestion instanceof MentorQuestion);
    assertEquals(updatedQuestion.getText(), updateText);

    verify(mentorQuestionRepository, times(1)).save(any());
    verify(mentorQuestionRepository, times(1))
        .findMentorQuestionByIdAndUserEmailAndEnrolledCourseId(any(), any(), any());
  }

  @Test
  void update_withIncorrectData_wrongQuestionId() {

    MentorQuestion mentorQuestion = TestHelper.createMentorQuestion();

    UpdateQuestionRequest request = new UpdateQuestionRequest();
    long wrongQuestionId = 999L;
    String email = mentorQuestion.getUser().getEmail();

    assertThatThrownBy(() -> questionService.update(request, wrongQuestionId, 1L, email))
        .isInstanceOf(IllegalArgumentException.class);

    verify(mentorQuestionRepository, times(0)).save(any());
    verify(mentorQuestionRepository, times(1))
        .findMentorQuestionByIdAndUserEmailAndEnrolledCourseId(any(), any(), any());
  }

  @Test
  void update_withIncorrectData_wrongUserEmail() {

    long mentorQuestionId = TestHelper.createMentorQuestion().getId();

    UpdateQuestionRequest request = new UpdateQuestionRequest();
    String wrongEmail = "wrongEmail";

    assertThatThrownBy(() -> questionService.update(request, mentorQuestionId, 1L, wrongEmail))
        .isInstanceOf(IllegalArgumentException.class);

    verify(mentorQuestionRepository, times(0)).save(any());
    verify(mentorQuestionRepository, times(1))
        .findMentorQuestionByIdAndUserEmailAndEnrolledCourseId(any(), any(), any());
  }

  @Test
  void delete_withCorrectData() {

    MentorQuestion mentorQuestion = TestHelper.createMentorQuestion();
    EnrolledCourse enrolledCourse = CourseGenerator.generateEnrolledCourse();
    mentorQuestion.setCourse(enrolledCourse.getCourse());
    int updatedRows = 1;

    given(
            mentorQuestionRepository.delete(
                mentorQuestion.getId(),
                mentorQuestion.getUser().getEmail(),
                enrolledCourse.getId()))
        .willReturn(updatedRows);
    questionService.delete(
        mentorQuestion.getId(), enrolledCourse.getId(), mentorQuestion.getUser().getEmail());

    assertEquals(1, updatedRows);
    verify(mentorQuestionRepository, times(1)).delete(any(), any(), any());
  }

  @Test
  void delete_withIncorrectData_wrongUserEmail() {

    MentorQuestion mentorQuestion = TestHelper.createMentorQuestion();
    EnrolledCourse enrolledCourse = CourseGenerator.generateEnrolledCourse();
    mentorQuestion.setCourse(enrolledCourse.getCourse());

    long mentorQuestionId = mentorQuestion.getId();
    long enrolledCourseId = enrolledCourse.getId();
    String wrongEmail = "wrongEmail";

    assertThatThrownBy(() -> questionService.delete(mentorQuestionId, enrolledCourseId, wrongEmail))
        .isInstanceOf(IllegalArgumentException.class);
    verify(mentorQuestionRepository, times(1)).delete(any(), any(), any());
  }

  @Test
  void delete_withIncorrectData_wrongQuestionId() {

    MentorQuestion mentorQuestion = TestHelper.createMentorQuestion();
    EnrolledCourse enrolledCourse = CourseGenerator.generateEnrolledCourse();
    mentorQuestion.setCourse(enrolledCourse.getCourse());

    String userEmail = mentorQuestion.getUser().getEmail();
    long enrolledCourseId = enrolledCourse.getId();
    long wrongQuestionId = 999L;

    assertThatThrownBy(() -> questionService.delete(wrongQuestionId, enrolledCourseId, userEmail))
        .isInstanceOf(IllegalArgumentException.class);
    verify(mentorQuestionRepository, times(1)).delete(any(), any(), any());
  }

  @Test
  void delete_withIncorrectData_wrongEnrolledCourseId() {

    MentorQuestion mentorQuestion = TestHelper.createMentorQuestion();
    EnrolledCourse enrolledCourse = CourseGenerator.generateEnrolledCourse();
    mentorQuestion.setCourse(enrolledCourse.getCourse());
    String userEmail = mentorQuestion.getUser().getEmail();
    long questionId = mentorQuestion.getId();
    long wrongEnrolledCourseId = 999L;

    assertThatThrownBy(() -> questionService.delete(questionId, wrongEnrolledCourseId, userEmail))
        .isInstanceOf(IllegalArgumentException.class);
    verify(mentorQuestionRepository, times(1)).delete(any(), any(), any());
  }

  @Test
  void findMentorQuestionByCourseId_withCorrectData() {
    MentorQuestion question = TestHelper.createMentorQuestion();
    long enrolledCourseId = 1L;
    Pageable pageable = PageRequest.of(0, 2);
    Page<MentorQuestion> questionPage = new PageImpl<>(List.of(question));

    when(mentorQuestionRepository.findQuestionByEnrolledCourseId(enrolledCourseId, pageable))
        .thenReturn(questionPage);
    Page<? extends Question> questionByCourseId =
        questionService.findQuestionByCourseId(enrolledCourseId, pageable);

    assertNotNull(questionByCourseId.stream().findFirst().orElseThrow());
    assertTrue(
        questionByCourseId.stream()
            .allMatch(peersQuestion -> peersQuestion instanceof MentorQuestion));
    verify(mentorQuestionRepository, times(1))
        .findQuestionByEnrolledCourseId(enrolledCourseId, pageable);
  }

  @Test
  void findMentorQuestionByCourseId_withIncorrectData() {
    long wrongEnrolledCourseId = 1L;
    Pageable pageable = PageRequest.of(0, 2);

    when(mentorQuestionRepository.findQuestionByEnrolledCourseId(wrongEnrolledCourseId, pageable))
        .thenReturn(Page.empty(pageable));
    Page<? extends Question> questionByCourseId =
        questionService.findQuestionByCourseId(wrongEnrolledCourseId, pageable);

    assertTrue(questionByCourseId.getContent().isEmpty());
    verify(mentorQuestionRepository, times(1))
        .findQuestionByEnrolledCourseId(wrongEnrolledCourseId, pageable);
  }

  @Test
  void findMentorQuestionByIdAndEnrolledCourseId_withCorrectData() {

    MentorQuestion question = TestHelper.createMentorQuestion();
    long questionId = question.getId();
    long enrolledCourseId = 1L;

    when(mentorQuestionRepository.findQuestionByIdAndEnrolledCourseId(enrolledCourseId, questionId))
        .thenReturn(Optional.of(question));
    Question questionByCourseId =
        questionService.findQuestionByIdAndEnrolledCourseId(enrolledCourseId, questionId);

    assertNotNull(questionByCourseId);
    assertTrue(questionByCourseId instanceof MentorQuestion);
    verify(mentorQuestionRepository, times(1))
        .findQuestionByIdAndEnrolledCourseId(enrolledCourseId, questionId);
  }

  @Test
  void findQuestionByIdAndEnrolledCourseId_withIncorrectData() {

    long wrongQuestionId = 999L;
    long enrolledCourseId = 1L;

    when(mentorQuestionRepository.findQuestionByIdAndEnrolledCourseId(
            enrolledCourseId, wrongQuestionId))
        .thenReturn(Optional.empty());

    assertThatThrownBy(
            () ->
                questionService.findQuestionByIdAndEnrolledCourseId(
                    enrolledCourseId, wrongQuestionId))
        .isInstanceOf(IllegalArgumentException.class);

    verify(mentorQuestionRepository, times(1))
        .findQuestionByIdAndEnrolledCourseId(enrolledCourseId, wrongQuestionId);
  }
}
