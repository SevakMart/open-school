package app.openschool.course.discussion.question;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.api.CourseGenerator;
import app.openschool.course.discussion.QuestionService;
import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.course.discussion.peers.question.PeersQuestionRepository;
import app.openschool.course.discussion.peers.question.PeersQuestionServiceImpl;
import app.openschool.user.User;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PeersQuestionServiceImplTest {
  @Mock PeersQuestionRepository peersQuestionRepository;
  @Mock EnrolledCourseRepository enrolledCourseRepository;

  QuestionService questionService;

  @BeforeEach
  void setUp() {
    questionService =
        new PeersQuestionServiceImpl(peersQuestionRepository, enrolledCourseRepository);
  }

  @Test
  void addQuestion_withCorrectArguments_returnsCreatedQuestion() {
    PeersQuestion peersQuestion = TestHelper.createDiscussionPeersQuestion();
    given(peersQuestionRepository.save(any())).willReturn(peersQuestion);
    User user = TestHelper.createUser();
    Course course = TestHelper.crateCourse();
    EnrolledCourse enrolledCourse = TestHelper.createEnrolledCourse(user, course);
    doReturn(Optional.of(enrolledCourse)).when(enrolledCourseRepository).findById(anyLong());
    QuestionResponseDto questionResponseDto =
        questionService.create(
            enrolledCourse.getId(),
            crateDiscussionQuestionRequestDto(),
            TestHelper.createPrincipal().getName());
    assertEquals(peersQuestion.getId(), questionResponseDto.getId());
    assertEquals(peersQuestion.getText(), questionResponseDto.getText());
    assertEquals(peersQuestion.getUser().getId(), questionResponseDto.getUserDto().getId());
    assertEquals(peersQuestion.getUser().getName(), questionResponseDto.getUserDto().getName());
    assertEquals(peersQuestion.getUser().getEmail(), questionResponseDto.getUserDto().getEmail());
    assertEquals(peersQuestion.getCourse().getId(), questionResponseDto.getCourseDto().getId());
    assertEquals(
        peersQuestion.getCourse().getTitle(), questionResponseDto.getCourseDto().getTitle());
    verify(peersQuestionRepository, times(1)).save(any());
  }

  @Test
  void addQuestion_withInCorrectEnrolledCourseId() {

    given(enrolledCourseRepository.findById(anyLong())).willReturn(Optional.empty());

    assertThatThrownBy(
            () ->
                questionService.create(
                    anyLong(),
                    crateDiscussionQuestionRequestDto(),
                    TestHelper.createPrincipal().getName()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void addQuestion_catchPermissionDeniedException() {
    User user = TestHelper.createUser();
    EnrolledCourse enrolledCourse = new EnrolledCourse();
    enrolledCourse.setUser(new User(2L));
    doReturn(Optional.of(enrolledCourse))
        .when(enrolledCourseRepository)
        .findById(enrolledCourse.getId());

    assertThatThrownBy(
            () ->
                questionService.create(
                    enrolledCourse.getId(),
                    crateDiscussionQuestionRequestDto(),
                    TestHelper.createPrincipal().getName()))
        .isInstanceOf(PermissionDeniedException.class);
  }

  @Test
  void update_withCorrectData() {

    PeersQuestion peersQuestion = TestHelper.createDiscussionPeersQuestion();

    UpdateQuestionRequest request = new UpdateQuestionRequest();
    String updateText = "Question was updated";
    request.setText(updateText);

    given(
            peersQuestionRepository.findPeersQuestionByIdAndUserEmailAndEnrolledCourseId(
                anyLong(), anyString(), anyLong()))
        .willReturn(Optional.of(peersQuestion));
    given(peersQuestionRepository.save(any())).willReturn(peersQuestion);

    peersQuestion.setText(request.getText());

    PeersQuestion updatedQuestion =
        questionService.update(
            request, peersQuestion.getId(), 1L, peersQuestion.getUser().getEmail());
    assertEquals(updatedQuestion.getText(), updateText);

    verify(peersQuestionRepository, times(1)).save(any());
    verify(peersQuestionRepository, times(1))
        .findPeersQuestionByIdAndUserEmailAndEnrolledCourseId(any(), any(), any());
  }

  @Test
  void update_withIncorrectData_wrongQuestionId() {

    PeersQuestion peersQuestion = TestHelper.createDiscussionPeersQuestion();

    UpdateQuestionRequest request = new UpdateQuestionRequest();
    long wrongQuestionId = 999L;

    assertThatThrownBy(
            () ->
                questionService.update(
                    request, wrongQuestionId, 1L, peersQuestion.getUser().getEmail()))
        .isInstanceOf(IllegalArgumentException.class);

    verify(peersQuestionRepository, times(0)).save(any());
    verify(peersQuestionRepository, times(1))
        .findPeersQuestionByIdAndUserEmailAndEnrolledCourseId(any(), any(), any());
  }

  @Test
  void update_withIncorrectData_wrongUserEmail() {

    PeersQuestion peersQuestion = TestHelper.createDiscussionPeersQuestion();

    UpdateQuestionRequest request = new UpdateQuestionRequest();
    String wrongEmail = "wrongEmail";

    assertThatThrownBy(() -> questionService.update(request, peersQuestion.getId(), 1L, wrongEmail))
        .isInstanceOf(IllegalArgumentException.class);

    verify(peersQuestionRepository, times(0)).save(any());
    verify(peersQuestionRepository, times(1))
        .findPeersQuestionByIdAndUserEmailAndEnrolledCourseId(any(), any(), any());
  }

  @Test
  void delete_withCorrectData() {

    PeersQuestion peersQuestion = TestHelper.createDiscussionPeersQuestion();
    EnrolledCourse enrolledCourse = CourseGenerator.generateEnrolledCourse();
    peersQuestion.setCourse(enrolledCourse.getCourse());
    int updatedRows = 1;

    given(
            peersQuestionRepository.delete(
                peersQuestion.getId(), peersQuestion.getUser().getEmail(), enrolledCourse.getId()))
        .willReturn(updatedRows);
    questionService.delete(
        peersQuestion.getId(), enrolledCourse.getId(), peersQuestion.getUser().getEmail());

    assertEquals(1, updatedRows);
    verify(peersQuestionRepository, times(1)).delete(any(), any(), any());
  }

  @Test
  void delete_withIncorrectData_wrongUserEmail() {

    PeersQuestion peersQuestion = TestHelper.createDiscussionPeersQuestion();
    EnrolledCourse enrolledCourse = CourseGenerator.generateEnrolledCourse();
    peersQuestion.setCourse(enrolledCourse.getCourse());

    String wrongEmail = "wrongEmail";

    assertThatThrownBy(
            () -> questionService.delete(peersQuestion.getId(), enrolledCourse.getId(), wrongEmail))
        .isInstanceOf(IllegalArgumentException.class);
    verify(peersQuestionRepository, times(1)).delete(any(), any(), any());
  }

  @Test
  void delete_withIncorrectData_wrongQuestionId() {

    PeersQuestion peersQuestion = TestHelper.createDiscussionPeersQuestion();
    EnrolledCourse enrolledCourse = CourseGenerator.generateEnrolledCourse();
    peersQuestion.setCourse(enrolledCourse.getCourse());

    long wrongQuestionId = 999L;

    assertThatThrownBy(
            () ->
                questionService.delete(
                    wrongQuestionId, enrolledCourse.getId(), peersQuestion.getUser().getEmail()))
        .isInstanceOf(IllegalArgumentException.class);
    verify(peersQuestionRepository, times(1)).delete(any(), any(), any());
  }

  @Test
  void delete_withIncorrectData_wrongEnrolledCourseId() {

    PeersQuestion peersQuestion = TestHelper.createDiscussionPeersQuestion();
    EnrolledCourse enrolledCourse = CourseGenerator.generateEnrolledCourse();
    peersQuestion.setCourse(enrolledCourse.getCourse());

    long wrongEnrolledCourseId = 999L;

    assertThatThrownBy(
            () ->
                questionService.delete(
                    peersQuestion.getId(),
                    wrongEnrolledCourseId,
                    peersQuestion.getUser().getEmail()))
        .isInstanceOf(IllegalArgumentException.class);
    verify(peersQuestionRepository, times(1)).delete(any(), any(), any());
  }

  QuestionRequestDto crateDiscussionQuestionRequestDto() {
    QuestionRequestDto requestDto = new QuestionRequestDto();
    requestDto.setText("text");
    return requestDto;
  }
}
