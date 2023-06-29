package app.openschool.course.discussion.answer;

import static org.assertj.core.api.Assertions.assertThat;
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
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.Answer;
import app.openschool.course.discussion.AnswerService;
import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.UpdateAnswerRequest;
import app.openschool.course.discussion.mentor.answer.MentorAnswer;
import app.openschool.course.discussion.mentor.answer.MentorAnswerRepository;
import app.openschool.course.discussion.mentor.answer.MentorAnswerServiceImpl;
import app.openschool.course.discussion.mentor.question.MentorQuestion;
import app.openschool.course.discussion.mentor.question.MentorQuestionRepository;
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
class MentorAnswerServiceImplTest {

  @Mock MentorAnswerRepository mentorAnswerRepository;
  @Mock MentorQuestionRepository mentorQuestionRepository;
  @Mock EnrolledCourseRepository enrolledCourseRepository;
  AnswerService answerService;

  @BeforeEach
  void setUp() {
    answerService =
        new MentorAnswerServiceImpl(
            mentorAnswerRepository, mentorQuestionRepository, enrolledCourseRepository);
  }

  @Test
  void addAnswer_withCorrectArguments_returnsCreatedAnswer() {

    MentorAnswer mentorAnswer = createDiscussionAnswer();
    given(mentorAnswerRepository.save(any())).willReturn(mentorAnswer);
    User user = TestHelper.createUser();
    given(mentorQuestionRepository.findById(anyLong()))
        .willReturn(Optional.of(TestHelper.createMentorQuestion()));
    EnrolledCourse enrolledCourse = TestHelper.createEnrolledCourse(user, TestHelper.crateCourse());
    given(enrolledCourseRepository.findById(anyLong())).willReturn(Optional.of(enrolledCourse));
    Answer answerResponseDto =
        answerService.create(
            enrolledCourse.getId(),
            createDiscussionAnswerRequestDto(),
            TestHelper.createPrincipal().getName());

    assertTrue(answerResponseDto instanceof MentorAnswer);
    assertEquals(mentorAnswer.getId(), answerResponseDto.getId());
    assertEquals(mentorAnswer.getText(), answerResponseDto.getText());
    assertEquals(mentorAnswer.getUser().getId(), answerResponseDto.getUser().getId());
    assertEquals(mentorAnswer.getUser().getName(), answerResponseDto.getUser().getName());
    assertEquals(mentorAnswer.getUser().getEmail(), answerResponseDto.getUser().getEmail());
    verify(mentorAnswerRepository, times(1)).save(any());
  }

  @Test
  void addAnswer_withIncorrectQuestionId() {

    EnrolledCourse enrolledCourse =
        TestHelper.createEnrolledCourse(TestHelper.createUser(), TestHelper.crateCourse());

    given(enrolledCourseRepository.findById(anyLong())).willReturn(Optional.of(enrolledCourse));
    given(mentorQuestionRepository.findById(anyLong())).willReturn(Optional.empty());

    long enrolledCourseId = enrolledCourse.getId();
    String email = TestHelper.createPrincipal().getName();
    AnswerRequestDto answerRequestDto = createDiscussionAnswerRequestDto();
    answerRequestDto.setQuestionId(999L);

    assertThatThrownBy(() -> answerService.create(enrolledCourseId, answerRequestDto, email))
        .isInstanceOf(IllegalAccessError.class);
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

    given(mentorQuestionRepository.findById(anyLong()))
        .willReturn(Optional.of(TestHelper.createMentorQuestion()));
    doReturn(Optional.of(enrolledCourse)).when(enrolledCourseRepository).findById(enrolledCourseId);

    assertThatThrownBy(
            () -> answerService.create(enrolledCourseId, discussionAnswerRequestDto, email))
        .isInstanceOf(PermissionDeniedException.class);
  }

  @Test
  void update_withCorrectData() {

    MentorAnswer mentorAnswer = TestHelper.createMentorAnswer();

    UpdateAnswerRequest request = new UpdateAnswerRequest();
    String updateText = "Answer was updated";
    request.setText(updateText);

    given(
            mentorAnswerRepository.findMentorAnswerByIdAndUserEmailAndQuestionId(
                anyLong(), anyLong(), anyLong(), anyString()))
        .willReturn(Optional.of(mentorAnswer));
    given(mentorAnswerRepository.save(any())).willReturn(mentorAnswer);

    mentorAnswer.setText(request.getText());

    Answer updatedAnswer =
        answerService.update(
            request,
            mentorAnswer.getId(),
            mentorAnswer.getDiscussionQuestionMentor().getId(),
            mentorAnswer.getDiscussionQuestionMentor().getCourse().getId(),
            mentorAnswer.getUser().getEmail());
    assertTrue(updatedAnswer instanceof MentorAnswer);
    assertThat(updatedAnswer).isInstanceOf(MentorAnswer.class);
    assertEquals(updatedAnswer.getText(), updateText);

    verify(mentorAnswerRepository, times(1)).save(any());
    verify(mentorAnswerRepository, times(1))
        .findMentorAnswerByIdAndUserEmailAndQuestionId(any(), any(), any(), any());
  }

  @Test
  void update_withIncorrectData_wrongQuestionId() {

    MentorAnswer mentorAnswer = TestHelper.createMentorAnswer();

    UpdateAnswerRequest request = new UpdateAnswerRequest();
    long wrongAnswerId = 999L;

    long mentorQuestionId = mentorAnswer.getDiscussionQuestionMentor().getId();
    long courseId = mentorAnswer.getDiscussionQuestionMentor().getCourse().getId();
    String email = mentorAnswer.getUser().getEmail();

    assertThatThrownBy(
            () -> answerService.update(request, wrongAnswerId, mentorQuestionId, courseId, email))
        .isInstanceOf(IllegalArgumentException.class);

    verify(mentorAnswerRepository, times(0)).save(any());
    verify(mentorAnswerRepository, times(1))
        .findMentorAnswerByIdAndUserEmailAndQuestionId(any(), any(), any(), any());
  }

  @Test
  void update_withIncorrectData_wrongUserEmail() {

    long mentorAnswerId = TestHelper.createMentorAnswer().getId();

    UpdateAnswerRequest request = new UpdateAnswerRequest();
    String wrongEmail = "wrongEmail";

    assertThatThrownBy(() -> answerService.update(request, mentorAnswerId, 1L, 1L, wrongEmail))
        .isInstanceOf(IllegalArgumentException.class);

    verify(mentorAnswerRepository, times(0)).save(any());
    verify(mentorAnswerRepository, times(1))
        .findMentorAnswerByIdAndUserEmailAndQuestionId(any(), any(), any(), any());
  }

  @Test
  void delete_withCorrectData() {

    MentorAnswer mentorAnswer = TestHelper.createMentorAnswer();
    MentorQuestion mentorQuestion = TestHelper.createMentorQuestion();
    mentorAnswer.setDiscussionQuestionMentor(mentorQuestion);
    int updatedRows = 1;

    given(
            mentorAnswerRepository.delete(
                mentorAnswer.getId(),
                mentorQuestion.getId(),
                mentorQuestion.getCourse().getId(),
                mentorQuestion.getUser().getEmail()))
        .willReturn(updatedRows);
    answerService.delete(
        mentorAnswer.getId(),
        mentorQuestion.getId(),
        mentorQuestion.getCourse().getId(),
        mentorQuestion.getUser().getEmail());

    assertEquals(1, updatedRows);
    verify(mentorAnswerRepository, times(1)).delete(any(), any(), any(), any());
  }

  @Test
  void delete_withIncorrectData_wrongUserEmail() {

    MentorAnswer mentorAnswer = TestHelper.createMentorAnswer();
    MentorQuestion mentorQuestion = TestHelper.createMentorQuestion();
    mentorAnswer.setDiscussionQuestionMentor(mentorQuestion);

    long mentorAnswerId = mentorAnswer.getId();
    long mentorQuestionId = mentorQuestion.getId();
    long questionCourseId = mentorQuestion.getCourse().getId();
    String wrongEmail = "wrongEmail";

    assertThatThrownBy(
            () ->
                answerService.delete(
                    mentorAnswerId, mentorQuestionId, questionCourseId, wrongEmail))
        .isInstanceOf(IllegalArgumentException.class);
    verify(mentorAnswerRepository, times(1)).delete(any(), any(), any(), any());
  }

  @Test
  void delete_withIncorrectData_wrongQuestionId() {

    MentorAnswer mentorAnswer = TestHelper.createMentorAnswer();
    MentorQuestion mentorQuestion = TestHelper.createMentorQuestion();
    mentorAnswer.setDiscussionQuestionMentor(mentorQuestion);

    String userEmail = mentorAnswer.getUser().getEmail();
    long mentorAnswerId = mentorAnswer.getId();
    long wrongQuestionId = 999L;
    long courseId = mentorQuestion.getCourse().getId();

    assertThatThrownBy(
            () -> answerService.delete(mentorAnswerId, wrongQuestionId, courseId, userEmail))
        .isInstanceOf(IllegalArgumentException.class);
    verify(mentorAnswerRepository, times(1)).delete(any(), any(), any(), any());
  }

  @Test
  void delete_withIncorrectData_wrongEnrolledCourseId() {

    MentorAnswer mentorAnswer = TestHelper.createMentorAnswer();
    MentorQuestion mentorQuestion = TestHelper.createMentorQuestion();
    mentorAnswer.setDiscussionQuestionMentor(mentorQuestion);
    String userEmail = mentorAnswer.getUser().getEmail();
    long questionId = mentorAnswer.getDiscussionQuestionMentor().getId();
    long wrongEnrolledCourseId = 999L;
    Long mentorId = mentorAnswer.getId();

    assertThatThrownBy(
            () -> answerService.delete(mentorId, questionId, wrongEnrolledCourseId, userEmail))
        .isInstanceOf(IllegalArgumentException.class);
    verify(mentorAnswerRepository, times(1)).delete(any(), any(), any(), any());
  }

  @Test
  void findAnswerById_withCorrectData() {
    MentorAnswer mentorAnswer = TestHelper.createMentorAnswer();
    long mentorAnswerId = mentorAnswer.getId();
    when(mentorAnswerRepository.findById(mentorAnswerId)).thenReturn(Optional.of(mentorAnswer));
    Answer answerById = answerService.findAnswerById(mentorAnswerId);

    assertNotNull(answerById);
    assertTrue(answerById instanceof MentorAnswer);
    verify(mentorAnswerRepository, times(1)).findById(mentorAnswerId);
  }

  @Test
  void findAnswerById_withIncorrectData() {
    MentorAnswer mentorAnswer = TestHelper.createMentorAnswer();
    long wrongMentorAnswerId = mentorAnswer.getId();
    when(mentorAnswerRepository.findById(wrongMentorAnswerId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> answerService.findAnswerById(wrongMentorAnswerId))
        .isInstanceOf(IllegalArgumentException.class);
    verify(mentorAnswerRepository, times(1)).findById(wrongMentorAnswerId);
  }

  @Test
  void findAnswerByQuestionId_withCorrectData() {
    MentorAnswer mentorAnswer = TestHelper.createMentorAnswer();
    long questionId = mentorAnswer.getDiscussionQuestionMentor().getId();
    Pageable pageable = PageRequest.of(0, 2);
    Page<MentorAnswer> mentorAnswersPage = new PageImpl<>(List.of(mentorAnswer));
    when(mentorAnswerRepository.findMentorAnswerByMentorQuestionId(questionId, pageable))
        .thenReturn(mentorAnswersPage);
    Page<? extends Answer> answersByQuestionId =
        answerService.findAnswerByQuestionId(questionId, pageable);

    assertNotNull(answersByQuestionId.stream().findFirst().orElseThrow());
    assertTrue(answersByQuestionId.stream().allMatch(an -> an instanceof MentorAnswer));
    verify(mentorAnswerRepository, times(1))
        .findMentorAnswerByMentorQuestionId(questionId, pageable);
  }

  MentorAnswer createDiscussionAnswer() {
    MentorAnswer mentorAnswer = new MentorAnswer();
    mentorAnswer.setId(1L);
    mentorAnswer.setText("text");
    mentorAnswer.setUser(TestHelper.createUser());
    mentorAnswer.setDiscussionQuestionMentor(TestHelper.createMentorQuestion());

    return mentorAnswer;
  }

  AnswerRequestDto createDiscussionAnswerRequestDto() {
    AnswerRequestDto answerRequestDto = new AnswerRequestDto();
    answerRequestDto.setText("text");
    answerRequestDto.setQuestionId(1L);
    return answerRequestDto;
  }
}
