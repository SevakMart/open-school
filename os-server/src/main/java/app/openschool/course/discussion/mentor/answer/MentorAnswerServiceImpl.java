package app.openschool.course.discussion.mentor.answer;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.AnswerService;
import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.UpdateAnswerRequest;
import app.openschool.course.discussion.mentor.question.MentorQuestion;
import app.openschool.course.discussion.mentor.question.MentorQuestionRepository;
import java.time.Instant;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("discussionAnswerMentor")
public class MentorAnswerServiceImpl implements AnswerService {

  private final MentorAnswerRepository mentorAnswerRepository;
  private final MentorQuestionRepository mentorQuestionRepository;
  private final EnrolledCourseRepository enrolledCourseRepository;

  public MentorAnswerServiceImpl(
      MentorAnswerRepository mentorAnswerRepository,
      MentorQuestionRepository mentorQuestionRepository,
      EnrolledCourseRepository enrolledCourseRepository) {
    this.mentorAnswerRepository = mentorAnswerRepository;
    this.mentorQuestionRepository = mentorQuestionRepository;
    this.enrolledCourseRepository = enrolledCourseRepository;
  }

  @Override
  public MentorAnswer create(Long enrolledCourseId, AnswerRequestDto requestDto, String email) {

    EnrolledCourse extractedEnrolledCourse =
        enrolledCourseRepository.findById(enrolledCourseId).orElseThrow(IllegalAccessError::new);

    MentorQuestion extractedMentorQuestion =
        mentorQuestionRepository
            .findById(requestDto.getQuestionId())
            .orElseThrow(IllegalAccessError::new);

    checkingDataConsistency(
        extractedMentorQuestion, extractedEnrolledCourse, email, requestDto.getQuestionId());

    return mentorAnswerRepository.save(
        prepareAnswer(extractedEnrolledCourse, requestDto, extractedMentorQuestion));
  }

  @Override
  public MentorAnswer update(
      UpdateAnswerRequest request,
      Long answerId,
      Long questionId,
      Long enrolledCourseId,
      String currentUserEmail) {
    MentorAnswer mentorAnswer =
        mentorAnswerRepository
            .findMentorAnswerByIdAndUserEmailAndQuestionId(
                answerId, questionId, enrolledCourseId, currentUserEmail)
            .orElseThrow(IllegalArgumentException::new);
    mentorAnswer.setText(request.getText());
    mentorAnswer.setCreatedDate(Instant.now());
    return mentorAnswerRepository.save(mentorAnswer);
  }

  @Override
  public void delete(
      Long answerId, Long questionId, Long enrolledCourseId, String currentUserEmail) {
    int updatedRows =
        mentorAnswerRepository.delete(answerId, questionId, enrolledCourseId, currentUserEmail);
    if (updatedRows == 0) throw new IllegalArgumentException();
  }

  @Override
  public MentorAnswer findAnswerById(Long answerId) {
    return mentorAnswerRepository.findById(answerId).orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public Page<MentorAnswer> findAnswerByQuestionId(Long questionId, Pageable pageable) {
    return mentorAnswerRepository.findMentorAnswerByMentorQuestionId(questionId, pageable);
  }

  private void checkingDataConsistency(
      MentorQuestion extractedMentorQuestion,
      EnrolledCourse enrolledCourse,
      String currentUserEmail,
      Long questionId) {

    String emailFromEnrolledCourse = enrolledCourse.getUser().getEmail();
    Long questionIdFromExtractedQuestion = extractedMentorQuestion.getId();
    Long courseIdFromExtractedQuestion = extractedMentorQuestion.getCourse().getId();

    if (!Objects.equals(emailFromEnrolledCourse, currentUserEmail)
        || !Objects.equals(questionIdFromExtractedQuestion, questionId)
        || !Objects.equals(enrolledCourse.getCourse().getId(), courseIdFromExtractedQuestion)) {

      throw new PermissionDeniedException("permission.denied");
    }
  }

  private MentorAnswer prepareAnswer(
      EnrolledCourse enrolledCourse, AnswerRequestDto requestDto, MentorQuestion mentorQuestion) {

    MentorAnswer mentorAnswer = new MentorAnswer();
    mentorAnswer.setText(requestDto.getText());
    mentorAnswer.setDiscussionQuestionMentor(mentorQuestion);
    mentorAnswer.setUser(enrolledCourse.getUser());
    mentorAnswer.setCreatedDate(Instant.now());

    return mentorAnswer;
  }
}
