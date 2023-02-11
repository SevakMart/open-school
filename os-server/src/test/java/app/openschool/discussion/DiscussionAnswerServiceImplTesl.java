package app.openschool.discussion;

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
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.discussion.dto.DiscussionAnswerRequestDto;
import app.openschool.discussion.dto.DiscussionAnswerResponseDto;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;



@ExtendWith(MockitoExtension.class)
public class DiscussionAnswerServiceImplTesl {
  @Mock DiscussionAnswerRepository discussionAnswerRepository;
  @Mock UserRepository userRepository;
  @Mock DiscussionQuestionRepository discussionQuestionRepository;
  @Mock EnrolledCourseRepository enrolledCourseRepository;
  DiscussionAnswerService discussionAnswerService;

  @BeforeEach
  void setUp() {
    discussionAnswerService =
        new DiscussionAnswerServiceImpl(
            discussionAnswerRepository,
            discussionQuestionRepository,
            userRepository,
            enrolledCourseRepository);
  }

  @Test
  void addAnswer_withCorrectArguments_returnsCreatedAnswer() {

    DiscussionAnswer discussionAnswer = createDiscussionAnswer();
    given(discussionAnswerRepository.save(any())).willReturn(discussionAnswer);
    User user = TestHelper.createUser();
    given(userRepository.findUserByEmail(anyString())).willReturn(user);
    given(discussionQuestionRepository.findById(anyLong()))
        .willReturn(Optional.of(TestHelper.createDiscussionQuestion()));
    given(enrolledCourseRepository.findByCourseId(anyLong()))
        .willReturn(Optional.of(TestHelper.createEnrolledCourse(user, TestHelper.crateCourse())));
    DiscussionAnswerResponseDto discussionAnswerResponseDto =
        discussionAnswerService.create(
            createDiscussionAnswerRequestDto(), TestHelper.createPrincipal());
    assertEquals(discussionAnswer.getId(), discussionAnswerResponseDto.getId());
    assertEquals(discussionAnswer.getText(), discussionAnswerResponseDto.getText());
    assertEquals(
        discussionAnswer.getUser().getId(), discussionAnswerResponseDto.getUserDto().getId());
    assertEquals(
        discussionAnswer.getUser().getName(), discussionAnswerResponseDto.getUserDto().getName());
    assertEquals(
        discussionAnswer.getUser().getEmail(), discussionAnswerResponseDto.getUserDto().getEmail());
    verify(discussionAnswerRepository, times(1)).save(any());
  }

  @Test
  void addAnswer_withIncorrectEnrolledDiscussionQuestionId() {

    given(discussionQuestionRepository.findById(anyLong())).willReturn(Optional.empty());

    assertThatThrownBy(
            () ->
                discussionAnswerService.create(
                    createDiscussionAnswerRequestDto(), TestHelper.createPrincipal()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void addAnswer_withIncorrectCourseId() {
    given(discussionQuestionRepository.findById(anyLong()))
        .willReturn(Optional.of(TestHelper.createDiscussionQuestion()));
    given(enrolledCourseRepository.findByCourseId(anyLong())).willReturn(Optional.empty());
    assertThatThrownBy(
            () ->
                discussionAnswerService.create(
                    createDiscussionAnswerRequestDto(), TestHelper.createPrincipal()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void addAnswer_catchPermissionDeniedException() {
    EnrolledCourse enrolledCourse = new EnrolledCourse();
    User anotherUser = new User();
    anotherUser.setEmail("anotherEmail@gmail.com");
    enrolledCourse.setUser(anotherUser);
    given(discussionQuestionRepository.findById(anyLong()))
        .willReturn(Optional.of(TestHelper.createDiscussionQuestion()));
    doReturn(Optional.of(enrolledCourse)).when(enrolledCourseRepository).findByCourseId(anyLong());
    assertThatThrownBy(
            () ->
                discussionAnswerService.create(
                    createDiscussionAnswerRequestDto(), TestHelper.createPrincipal()))
        .isInstanceOf(PermissionDeniedException.class);
  }

  DiscussionAnswer createDiscussionAnswer() {
    DiscussionAnswer discussionAnswer = new DiscussionAnswer();
    discussionAnswer.setId(1L);
    discussionAnswer.setText("text");
    discussionAnswer.setUser(TestHelper.createUser());
    discussionAnswer.setDiscussionQuestion(TestHelper.createDiscussionQuestion());

    return discussionAnswer;
  }

  DiscussionAnswerRequestDto createDiscussionAnswerRequestDto() {
    return new DiscussionAnswerRequestDto("text", 1L);
  }
}
