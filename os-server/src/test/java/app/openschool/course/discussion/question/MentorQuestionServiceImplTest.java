package app.openschool.course.discussion.question;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.Question;
import app.openschool.course.discussion.QuestionService;
import app.openschool.course.discussion.TestHelper;
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
public class MentorQuestionServiceImplTest {

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
  void findMentorQuestionByCourseId_withCorrectData() {
    MentorQuestion question = TestHelper.createMentorQuestion();
    long enrolledCourseId = 1L;
    Pageable pageable = PageRequest.of(0, 2);
    Page<MentorQuestion> questionPage = new PageImpl<>(List.of(question));
    String searchQuery = "Question";

    when(mentorQuestionRepository.findQuestionByEnrolledCourseId(
            enrolledCourseId, pageable, searchQuery))
        .thenReturn(questionPage);
    Page<? extends Question> questionByCourseId =
        questionService.findQuestionByCourseId(enrolledCourseId, pageable, searchQuery);

    assertNotNull(questionByCourseId.stream().findFirst().orElseThrow());
    assertTrue(
        questionByCourseId.stream()
            .allMatch(peersQuestion -> peersQuestion instanceof MentorQuestion));
    verify(mentorQuestionRepository, times(1))
        .findQuestionByEnrolledCourseId(enrolledCourseId, pageable, searchQuery);
  }

  @Test
  void findMentorQuestionByCourseId_withIncorrectData() {
    long wrongEnrolledCourseId = 1L;
    Pageable pageable = PageRequest.of(0, 2);
    String searchQuery = "";

    when(mentorQuestionRepository.findQuestionByEnrolledCourseId(
            wrongEnrolledCourseId, pageable, searchQuery))
        .thenReturn(Page.empty(pageable));
    Page<? extends Question> questionByCourseId =
        questionService.findQuestionByCourseId(wrongEnrolledCourseId, pageable, searchQuery);

    assertTrue(questionByCourseId.getContent().isEmpty());
    verify(mentorQuestionRepository, times(1))
        .findQuestionByEnrolledCourseId(wrongEnrolledCourseId, pageable, searchQuery);
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
