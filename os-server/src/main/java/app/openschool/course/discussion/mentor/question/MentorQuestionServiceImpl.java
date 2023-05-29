package app.openschool.course.discussion.mentor.question;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.QuestionService;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import java.time.Instant;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("discussionQuestionMentor")
public class MentorQuestionServiceImpl implements QuestionService {
  private final EnrolledCourseRepository enrolledCourseRepository;
  private final MentorQuestionRepository mentorQuestionRepository;

  public MentorQuestionServiceImpl(
      EnrolledCourseRepository enrolledCourseRepository,
      MentorQuestionRepository mentorQuestionRepository) {
    this.enrolledCourseRepository = enrolledCourseRepository;
    this.mentorQuestionRepository = mentorQuestionRepository;
  }

  @Override
  public MentorQuestion create(Long enrolledCourseId, QuestionRequestDto requestDto, String email) {

    EnrolledCourse enrolledCourse =
        enrolledCourseRepository
            .findById(enrolledCourseId)
            .orElseThrow(IllegalArgumentException::new);

    checkingDataConsistency(enrolledCourse, email);

    return mentorQuestionRepository.save(prepareMentorQuestion(requestDto, enrolledCourse));
  }

  @Override
  public MentorQuestion update(
      UpdateQuestionRequest request,
      Long questionId,
      Long enrolledCourseId,
      String currentUserEmail) {
    MentorQuestion mentorQuestion =
            mentorQuestionRepository
            .findMentorQuestionByIdAndUserEmailAndEnrolledCourseId(questionId, currentUserEmail, enrolledCourseId)
            .orElseThrow(IllegalArgumentException::new);
    mentorQuestion.setText(request.getText());
    mentorQuestion.setCreatedDate(Instant.now());
    return mentorQuestionRepository.save(mentorQuestion);
  }

  @Override
  public void delete(Long questionId, Long enrolledCourseId, String currentUserEmail) {
    int updatedRows =
        mentorQuestionRepository.delete(questionId, currentUserEmail, enrolledCourseId);
    if (updatedRows == 0) throw new IllegalArgumentException();
  }

  @Override
  public MentorQuestion findQuestionByIdAndEnrolledCourseId(
      Long enrolledCourseId, Long questionId) {
    return mentorQuestionRepository
        .findQuestionByIdAndEnrolledCourseId(enrolledCourseId, questionId)
        .orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public Page<MentorQuestion> findQuestionByCourseId(Long enrolledCourseId, Pageable pageable) {

    return mentorQuestionRepository.findQuestionByEnrolledCourseId(enrolledCourseId, pageable);
  }

  private MentorQuestion prepareMentorQuestion(
      QuestionRequestDto requestDto, EnrolledCourse enrolledCourse) {

    MentorQuestion mentorQuestion = new MentorQuestion();
    mentorQuestion.setText(requestDto.getText());
    mentorQuestion.setUser(enrolledCourse.getUser());
    mentorQuestion.setCourse(enrolledCourse.getCourse());
    mentorQuestion.setCreatedDate(Instant.now());

    return mentorQuestion;
  }

  private void checkingDataConsistency(EnrolledCourse enrolledCourse, String currentUserEmail) {

    String emailFromEnrolledCourse = enrolledCourse.getUser().getEmail();

    if (!Objects.equals(emailFromEnrolledCourse, currentUserEmail)) {

      throw new PermissionDeniedException("permission.denied");
    }
  }
}
