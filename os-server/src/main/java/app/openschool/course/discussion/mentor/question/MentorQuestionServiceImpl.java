package app.openschool.course.discussion.mentor.question;

import app.openschool.common.exceptionhandler.exception.InvalidSearchQueryException;
import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.QuestionService;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import java.time.Instant;
import java.util.Locale;
import java.util.Objects;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("discussionQuestionMentor")
public class MentorQuestionServiceImpl implements QuestionService {
  private final EnrolledCourseRepository enrolledCourseRepository;
  private final MentorQuestionRepository mentorQuestionRepository;

  private final MessageSource messageSource;

  public MentorQuestionServiceImpl(
      EnrolledCourseRepository enrolledCourseRepository,
      MentorQuestionRepository mentorQuestionRepository,
      MessageSource messageSource) {
    this.enrolledCourseRepository = enrolledCourseRepository;
    this.mentorQuestionRepository = mentorQuestionRepository;
    this.messageSource = messageSource;
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
    return null;
  }

  @Override
  public void delete(Long questionId, Long enrolledCourseId, String currentUserEmail) {}

  @Override
  public MentorQuestion findQuestionByIdAndEnrolledCourseId(
      Long enrolledCourseId, Long questionId) {
    return mentorQuestionRepository
        .findQuestionByIdAndEnrolledCourseId(enrolledCourseId, questionId)
        .orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public Page<MentorQuestion> findQuestionByCourseId(
      Long enrolledCourseId, Pageable pageable, String searchQuery) {
    if (searchQuery != null && searchQuery.length() < 3) {
      String message =
          messageSource.getMessage("discussion.mentor.question.query.size", null, Locale.ROOT);
      throw new InvalidSearchQueryException(message);
    }
    return mentorQuestionRepository.findQuestionByEnrolledCourseId(
        enrolledCourseId, pageable, searchQuery);
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
