package app.openschool.course.discussion.answer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.AnswerService;
import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.course.discussion.peers.answer.PeersAnswer;
import app.openschool.course.discussion.peers.answer.PeersAnswerRepository;
import app.openschool.course.discussion.peers.answer.PeersAnswerServiceImpl;
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
    AnswerResponseDto answerResponseDto =
        answerService.create(
            enrolledCourse.getId(),
            createDiscussionAnswerRequestDto(),
            TestHelper.createPrincipal().getName());
    assertEquals(peersAnswer.getId(), answerResponseDto.getId());
    assertEquals(peersAnswer.getText(), answerResponseDto.getText());
    assertEquals(peersAnswer.getUser().getId(), answerResponseDto.getUserDto().getId());
    assertEquals(peersAnswer.getUser().getName(), answerResponseDto.getUserDto().getName());
    assertEquals(peersAnswer.getUser().getEmail(), answerResponseDto.getUserDto().getEmail());
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
  void findAnswerById_withCorrectData() {
    PeersAnswer answer = TestHelper.createDiscussionPeersAnswer();
    long answerId = answer.getId();
    when(peersAnswerRepository.findById(answerId)).thenReturn(Optional.of(answer));
    PeersAnswer answerById = answerService.findAnswerById(answerId);

    assertNotNull(answerById);
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
    Page<PeersAnswer> answersByQuestionId =
        answerService.findAnswerByQuestionId(questionId, pageable);

    assertNotNull(answersByQuestionId.stream().findFirst().orElseThrow());
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
