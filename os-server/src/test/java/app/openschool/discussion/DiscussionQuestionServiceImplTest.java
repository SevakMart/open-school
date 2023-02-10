package app.openschool.discussion;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.discussion.dto.DiscussionQuestionRequestDto;
import app.openschool.discussion.dto.DiscussionQuestionResponseDto;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DiscussionQuestionServiceImplTest {
  @Mock DiscussionQuestionRepository discussionQuestionRepository;
  @Mock CourseRepository courseRepository;
  @Mock UserRepository userRepository;
  @Mock EnrolledCourseRepository enrolledCourseRepository;

  DiscussionQuestionService discussionQuestionService;

  @BeforeEach
  void setUp() {
    discussionQuestionService =
        new DiscussionQuestionServiceImpl(
            discussionQuestionRepository,
            courseRepository,
            userRepository,
            enrolledCourseRepository);
  }

  @Test
  void addQuestion_withCorrectArguments_returnsCreatedQuestion() {
    DiscussionQuestion discussionQuestion = TestHelper.createDiscussionQuestion();
    given(discussionQuestionRepository.save(any())).willReturn(discussionQuestion);
    User user = TestHelper.createUser();
    Course course = TestHelper.crateCourse();
    given(userRepository.findUserByEmail(anyString())).willReturn(user);
    EnrolledCourse enrolledCourse = TestHelper.createEnrolledCourse(user, course);
    doReturn(Optional.of(enrolledCourse)).when(enrolledCourseRepository).findByCourseId(anyLong());
    given(courseRepository.findById(anyLong())).willReturn(Optional.of(course));
    DiscussionQuestionResponseDto discussionQuestionResponseDto =
        discussionQuestionService.create(
            crateDiscussionQuestionRequestDto(), TestHelper.createPrincipal());
    assertEquals(discussionQuestion.getId(), discussionQuestionResponseDto.getId());
    assertEquals(discussionQuestion.getText(), discussionQuestionResponseDto.getText());
    assertEquals(
        discussionQuestion.getUser().getId(), discussionQuestionResponseDto.getUserDto().getId());
    assertEquals(
        discussionQuestion.getUser().getName(),
        discussionQuestionResponseDto.getUserDto().getName());
    assertEquals(
        discussionQuestion.getUser().getEmail(),
        discussionQuestionResponseDto.getUserDto().getEmail());
    assertEquals(
        discussionQuestion.getCourse().getId(),
        discussionQuestionResponseDto.getCourseDto().getId());
    assertEquals(
        discussionQuestion.getCourse().getTitle(),
        discussionQuestionResponseDto.getCourseDto().getTitle());
    verify(discussionQuestionRepository, times(1)).save(any());
  }

  @Test
  void addQuestion_withInCorrectEnrolledCourseId() {

    given(enrolledCourseRepository.findByCourseId(anyLong())).willReturn(Optional.empty());

    assertThatThrownBy(
            () ->
                discussionQuestionService.create(
                    crateDiscussionQuestionRequestDto(), TestHelper.createPrincipal()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void addQuestion_withInCorrectCourseId() {
    User user = TestHelper.createUser();
    EnrolledCourse enrolledCourse = TestHelper.createEnrolledCourse(user, TestHelper.crateCourse());
    given(userRepository.findUserByEmail(anyString())).willReturn(user);
    given(enrolledCourseRepository.findByCourseId(anyLong()))
        .willReturn(Optional.of(enrolledCourse));
    given(courseRepository.findById(anyLong())).willReturn(Optional.empty());
    assertThatThrownBy(
            () ->
                discussionQuestionService.create(
                    crateDiscussionQuestionRequestDto(), TestHelper.createPrincipal()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void addQuestion_catchPermissionDeniedException() {
    User user = TestHelper.createUser();
    EnrolledCourse enrolledCourse = new EnrolledCourse();
    enrolledCourse.setUser(new User(2L));
    given(userRepository.findUserByEmail(anyString())).willReturn(user);
    doReturn(Optional.of(enrolledCourse)).when(enrolledCourseRepository).findByCourseId(anyLong());

    assertThatThrownBy(
            () ->
                discussionQuestionService.create(
                    crateDiscussionQuestionRequestDto(), TestHelper.createPrincipal()))
        .isInstanceOf(PermissionDeniedException.class);
  }

  DiscussionQuestionRequestDto crateDiscussionQuestionRequestDto() {
    return new DiscussionQuestionRequestDto("text", 1L);
  }
}
