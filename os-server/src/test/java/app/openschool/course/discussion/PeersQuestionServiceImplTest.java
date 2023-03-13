package app.openschool.course.discussion;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.QuestionResponseDto;
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
    PeersQuestion peersQuestion = TestHelper.createDiscussionQuestion();
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

  QuestionRequestDto crateDiscussionQuestionRequestDto() {
    QuestionRequestDto requestDto = new QuestionRequestDto();
    requestDto.setText("text");
    return requestDto;
  }
}
