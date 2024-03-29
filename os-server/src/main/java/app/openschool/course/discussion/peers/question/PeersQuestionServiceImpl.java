package app.openschool.course.discussion.peers.question;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.QuestionService;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import app.openschool.course.discussion.util.ValidationHandler;
import java.time.Instant;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("discussionQuestion")
public class PeersQuestionServiceImpl implements QuestionService {

  private final PeersQuestionRepository peersQuestionRepository;
  private final EnrolledCourseRepository enrolledCourseRepository;

  private final ValidationHandler validationHandler;

  public PeersQuestionServiceImpl(
      PeersQuestionRepository peersQuestionRepository,
      EnrolledCourseRepository enrolledCourseRepository,
      ValidationHandler validationHandler) {
    this.peersQuestionRepository = peersQuestionRepository;
    this.enrolledCourseRepository = enrolledCourseRepository;
    this.validationHandler = validationHandler;
  }

  @Override
  public PeersQuestion create(Long enrolledCourseId, QuestionRequestDto requestDto, String email) {

    EnrolledCourse extractedEnrolledCourse =
        enrolledCourseRepository
            .findById(enrolledCourseId)
            .orElseThrow(IllegalArgumentException::new);

    checkingDataConsistency(extractedEnrolledCourse, email);

    return peersQuestionRepository.save(prepareQuestion(requestDto, extractedEnrolledCourse));
  }

  @Override
  public PeersQuestion update(
      UpdateQuestionRequest request,
      Long questionId,
      Long enrolledCourseId,
      String currentUserEmail) {

    PeersQuestion peersQuestion =
        peersQuestionRepository
            .findPeersQuestionByIdAndUserEmailAndEnrolledCourseId(
                questionId, currentUserEmail, enrolledCourseId)
            .orElseThrow(IllegalArgumentException::new);

    peersQuestion.setText(request.getText());
    peersQuestion.setCreatedDate(Instant.now());

    return peersQuestionRepository.save(peersQuestion);
  }

  @Override
  public void delete(Long questionId, Long enrolledCourseId, String currentUserEmail) {

    int updatedRows =
        peersQuestionRepository.delete(questionId, currentUserEmail, enrolledCourseId);
    if (updatedRows == 0) {
      throw new IllegalArgumentException();
    }
  }

  @Override
  public Page<PeersQuestion> findQuestionByCourseId(
      Long enrolledCourseId, String userEmail, Pageable pageable, String searchQuery) {
    validationHandler.validateSearchQuery(searchQuery);
    return peersQuestionRepository.findQuestionByEnrolledCourseId(
        enrolledCourseId, userEmail, pageable, searchQuery);
  }

  @Override
  public PeersQuestion findQuestionByIdAndEnrolledCourseId(Long enrolledCourseId, Long questionId) {
    return peersQuestionRepository
        .findQuestionByIdAndEnrolledCourseId(enrolledCourseId, questionId)
        .orElseThrow(IllegalArgumentException::new);
  }

  private PeersQuestion prepareQuestion(
      QuestionRequestDto requestDto, EnrolledCourse enrolledCourse) {
    PeersQuestion peersQuestion = new PeersQuestion();

    peersQuestion.setText(requestDto.getText());
    peersQuestion.setUser(enrolledCourse.getUser());
    peersQuestion.setCreatedDate(Instant.now());
    peersQuestion.setCourse(enrolledCourse.getCourse());

    return peersQuestion;
  }

  private void checkingDataConsistency(EnrolledCourse enrolledCourse, String currentUserEmail) {

    String emailFromEnrolledCourse = enrolledCourse.getUser().getEmail();

    if (!Objects.equals(emailFromEnrolledCourse, currentUserEmail)) {

      throw new PermissionDeniedException("permission.denied");
    }
  }
}
