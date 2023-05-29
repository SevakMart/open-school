package app.openschool.course.discussion.answer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.Answer;
import app.openschool.course.discussion.AnswerService;
import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.UpdateAnswerRequest;
import app.openschool.course.discussion.peers.answer.PeersAnswer;
import app.openschool.course.discussion.peers.answer.PeersAnswerRepository;
import app.openschool.course.discussion.peers.answer.PeersAnswerServiceImpl;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.course.discussion.peers.question.PeersQuestionRepository;
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
class PeersAnswerServiceImplTest {
  @Mock PeersAnswerRepository peersAnswerRepository;
  @Mock PeersQuestionRepository peersQuestionRepository;
  @Mock EnrolledCourseRepository enrolledCourseRepository;
  AnswerService answerService;

  @BeforeEach
  void setUp() {
    answerService =
        new PeersAnswerServiceImpl(
            peersAnswerRepository, peersQuestionRepository, enrolledCourseRepository);
  }

  @Test
  void addAnswer_withCorrectArguments_returnsCreatedAnswer() {

    PeersAnswer peersAnswer = createDiscussionAnswer();
    given(peersAnswerRepository.save(any())).willReturn(peersAnswer);
    User user = TestHelper.createUser();
    given(peersQuestionRepository.findById(anyLong()))
        .willReturn(Optional.of(TestHelper.createDiscussionPeersQuestion()));
    EnrolledCourse enrolledCourse = TestHelper.createEnrolledCourse(user, TestHelper.crateCourse());
    given(enrolledCourseRepository.findById(anyLong())).willReturn(Optional.of(enrolledCourse));
    Answer answerResponseDto =
        answerService.create(
            enrolledCourse.getId(),
            createDiscussionAnswerRequestDto(),
            TestHelper.createPrincipal().getName());

    assertTrue(answerResponseDto instanceof PeersAnswer);
    assertEquals(peersAnswer.getId(), answerResponseDto.getId());
    assertEquals(peersAnswer.getText(), answerResponseDto.getText());
    assertEquals(peersAnswer.getUser().getId(), answerResponseDto.getUser().getId());
    assertEquals(peersAnswer.getUser().getName(), answerResponseDto.getUser().getName());
    assertEquals(peersAnswer.getUser().getEmail(), answerResponseDto.getUser().getEmail());
    verify(peersAnswerRepository, times(1)).save(any());
  }

  @Test
  void addAnswer_withIncorrectQuestionId() {

    EnrolledCourse enrolledCourse =
        TestHelper.createEnrolledCourse(TestHelper.createUser(), TestHelper.crateCourse());

    given(enrolledCourseRepository.findById(anyLong())).willReturn(Optional.of(enrolledCourse));
    given(peersQuestionRepository.findById(anyLong())).willReturn(Optional.empty());

    long enrolledCourseId = enrolledCourse.getId();
    String email = TestHelper.createPrincipal().getName();
    AnswerRequestDto answerRequestDto = createDiscussionAnswerRequestDto();
    answerRequestDto.setQuestionId(999L);

    assertThatThrownBy(() -> answerService.create(enrolledCourseId, answerRequestDto, email))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void addAnswer_catchPermissionDeniedException() {
    EnrolledCourse enrolledCourse = new EnrolledCourse();
    User anotherUser = new User();
    anotherUser.setEmail("anotherEmail@gmail.com");
    enrolledCourse.setUser(anotherUser);
    long enrolledCourseId = 1L;
    AnswerRequestDto discussionAnswerRequestDto = createDiscussionAnswerRequestDto();
    String email = TestHelper.createPrincipal().getName();

    given(peersQuestionRepository.findById(anyLong()))
        .willReturn(Optional.of(TestHelper.createDiscussionPeersQuestion()));
    doReturn(Optional.of(enrolledCourse)).when(enrolledCourseRepository).findById(enrolledCourseId);

    assertThatThrownBy(
            () -> answerService.create(enrolledCourseId, discussionAnswerRequestDto, email))
        .isInstanceOf(PermissionDeniedException.class);
  }

  @Test
  void update_withCorrectData() {

    PeersAnswer peersAnswer = TestHelper.createDiscussionPeersAnswer();

    UpdateAnswerRequest request = new UpdateAnswerRequest();
    String updateText = "Answer was updated";
    request.setText(updateText);

    given(
            peersAnswerRepository.findPeersAnswerByIdAndUserEmailAndQuestionId(
                    anyLong(),anyLong(),anyLong(), anyString()))
            .willReturn(Optional.of(peersAnswer));
    given(peersAnswerRepository.save(any())).willReturn(peersAnswer);

    peersAnswer.setText(request.getText());

    Answer updatedAnswer =
            answerService.update(
                    request, peersAnswer.getId(), peersAnswer.getDiscussionQuestion().getId(),peersAnswer.getDiscussionQuestion().getCourse().getId(), peersAnswer.getUser().getEmail());
    assertTrue(updatedAnswer instanceof PeersAnswer);
    assertEquals(updatedAnswer.getText(), updateText);

    verify(peersAnswerRepository, times(1)).save(any());
    verify(peersAnswerRepository, times(1))
            .findPeersAnswerByIdAndUserEmailAndQuestionId(any(), any(), any(),any());
  }

  @Test
  void update_withIncorrectData_wrongQuestionId() {

    PeersAnswer peersAnswer = TestHelper.createDiscussionPeersAnswer();

    UpdateAnswerRequest request = new UpdateAnswerRequest();
    long wrongAnswerId = 999L;
    long questionId = peersAnswer.getDiscussionQuestion().getId();
    Long courseId = peersAnswer.getDiscussionQuestion().getCourse().getId();
    String email = peersAnswer.getUser().getEmail();

    assertThatThrownBy(
            () ->
                    answerService.update(
                            request, wrongAnswerId, questionId , courseId
                            ,email))
            .isInstanceOf(IllegalArgumentException.class);

    verify(peersAnswerRepository, times(0)).save(any());
    verify(peersAnswerRepository, times(1))
            .findPeersAnswerByIdAndUserEmailAndQuestionId(any(), any(), any(),any());
  }

  @Test
  void update_withIncorrectData_wrongUserEmail() {

    long peersAnswerId = TestHelper.createDiscussionPeersAnswer().getId();

    UpdateAnswerRequest request = new UpdateAnswerRequest();
    String wrongEmail = "wrongEmail";

    assertThatThrownBy(() -> answerService.update(request, peersAnswerId, 1L, 1L,wrongEmail))
            .isInstanceOf(IllegalArgumentException.class);

    verify(peersAnswerRepository, times(0)).save(any());
    verify(peersAnswerRepository, times(1))
            .findPeersAnswerByIdAndUserEmailAndQuestionId(any(), any(), any(),any());
  }

  @Test
  void delete_withCorrectData() {

    PeersAnswer peersAnswer = TestHelper.createDiscussionPeersAnswer();
    PeersQuestion peersQuestion = TestHelper.createDiscussionPeersQuestion();
    peersAnswer.setDiscussionQuestion(peersQuestion);
    int updatedRows = 1;

    given(
            peersAnswerRepository.delete(
                    peersAnswer.getId(), peersQuestion.getId(), peersQuestion.getCourse().getId(), peersQuestion.getUser().getEmail()))
            .willReturn(updatedRows);
    answerService.delete(
            peersAnswer.getId(), peersQuestion.getId(), peersQuestion.getCourse().getId(),peersQuestion.getUser().getEmail());

    assertEquals(1, updatedRows);
    verify(peersAnswerRepository, times(1)).delete(any(), any(),any(), any());
  }

  @Test
  void delete_withIncorrectData_wrongUserEmail() {

    PeersAnswer peersAnswer = TestHelper.createDiscussionPeersAnswer();
    PeersQuestion peersQuestion = TestHelper.createDiscussionPeersQuestion();
    peersAnswer.setDiscussionQuestion(peersQuestion);

    long peersAnswerId = peersAnswer.getId();
    long peersQuestionId = peersQuestion.getId();
    String wrongEmail = "wrongEmail";
    long courseId = peersQuestion.getCourse().getId();

    assertThatThrownBy(() -> answerService.delete(peersAnswerId, peersQuestionId, courseId ,wrongEmail))
            .isInstanceOf(IllegalArgumentException.class);
    verify(peersAnswerRepository, times(1)).delete(any(), any(),any() ,any());
  }

  @Test
  void delete_withIncorrectData_wrongQuestionId() {

    PeersAnswer peersAnswer = TestHelper.createDiscussionPeersAnswer();
    PeersQuestion peersQuestion = TestHelper.createDiscussionPeersQuestion();
    peersAnswer.setDiscussionQuestion(peersQuestion);

    String userEmail = peersAnswer.getUser().getEmail();
    long peersAnswerId = peersAnswer.getId();
    long wrongQuestionId = 999L;
    Long courseId = peersQuestion.getCourse().getId();

    assertThatThrownBy(() -> answerService.delete(peersAnswerId,wrongQuestionId, courseId , userEmail))
            .isInstanceOf(IllegalArgumentException.class);
    verify(peersAnswerRepository, times(1)).delete(any(), any(), any(),any());
  }

  @Test
  void delete_withIncorrectData_wrongEnrolledCourseId() {

    PeersAnswer peersAnswer = TestHelper.createDiscussionPeersAnswer();
    PeersQuestion peersQuestion = TestHelper.createDiscussionPeersQuestion();
    peersAnswer.setDiscussionQuestion(peersQuestion);
    String userEmail = peersAnswer.getUser().getEmail();
    long questionId = peersAnswer.getDiscussionQuestion().getId();
    long wrongEnrolledCourseId = 999L;
    Long answerId = peersAnswer.getId();

    assertThatThrownBy(() -> answerService.delete(answerId,questionId, wrongEnrolledCourseId, userEmail))
            .isInstanceOf(IllegalArgumentException.class);
    verify(peersAnswerRepository, times(1)).delete(any(), any(), any(),any());
  }

  @Test
  void findAnswerById_withCorrectData() {
    PeersAnswer answer = TestHelper.createDiscussionPeersAnswer();
    long answerId = answer.getId();
    when(peersAnswerRepository.findById(answerId)).thenReturn(Optional.of(answer));
    Answer answerById = answerService.findAnswerById(answerId);

    assertNotNull(answerById);
    assertTrue(answerById instanceof PeersAnswer);
    verify(peersAnswerRepository, times(1)).findById(answerId);
  }

  @Test
  void findAnswerById_withIncorrectData() {
    PeersAnswer answer = TestHelper.createDiscussionPeersAnswer();
    long wrongAnswerId = answer.getId();
    when(peersAnswerRepository.findById(wrongAnswerId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> answerService.findAnswerById(wrongAnswerId))
        .isInstanceOf(IllegalArgumentException.class);
    verify(peersAnswerRepository, times(1)).findById(wrongAnswerId);
  }

  @Test
  void findAnswerByQuestionId_withCorrectData() {
    PeersAnswer answer = TestHelper.createDiscussionPeersAnswer();
    long questionId = answer.getDiscussionQuestion().getId();
    Pageable pageable = PageRequest.of(0, 2);
    Page<PeersAnswer> answersPage = new PageImpl<>(List.of(answer));
    when(peersAnswerRepository.findPeersAnswerByPeersQuestionId(questionId, pageable))
        .thenReturn(answersPage);
    Page<? extends Answer> answersByQuestionId =
        answerService.findAnswerByQuestionId(questionId, pageable);

    assertNotNull(answersByQuestionId.stream().findFirst().orElseThrow());
    assertTrue(answersByQuestionId.stream().allMatch(an -> an instanceof PeersAnswer));
    verify(peersAnswerRepository, times(1)).findPeersAnswerByPeersQuestionId(questionId, pageable);
  }

  PeersAnswer createDiscussionAnswer() {
    PeersAnswer peersAnswer = new PeersAnswer();
    peersAnswer.setId(1L);
    peersAnswer.setText("text");
    peersAnswer.setUser(TestHelper.createUser());
    peersAnswer.setDiscussionQuestion(TestHelper.createDiscussionPeersQuestion());

    return peersAnswer;
  }

  AnswerRequestDto createDiscussionAnswerRequestDto() {
    AnswerRequestDto answerRequestDto = new AnswerRequestDto();
    answerRequestDto.setText("text");
    answerRequestDto.setQuestionId(1L);
    return answerRequestDto;
  }
}
