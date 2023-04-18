package app.openschool.course.discussion.peers.answer;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.AnswerService;
import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.course.discussion.mapper.AnswerMapper;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.course.discussion.peers.question.PeersQuestionRepository;
import java.time.Instant;
import java.util.Objects;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service("discussionAnswer")
public class PeersAnswerServiceImpl implements AnswerService {
  private final PeersAnswerRepository peersAnswerRepository;
  private final PeersQuestionRepository peersQuestionRepository;
  private final EnrolledCourseRepository enrolledCourseRepository;

  public PeersAnswerServiceImpl(
      PeersAnswerRepository peersAnswerRepository,
      PeersQuestionRepository peersQuestionRepository,
      EnrolledCourseRepository enrolledCourseRepository) {
    this.peersAnswerRepository = peersAnswerRepository;
    this.peersQuestionRepository = peersQuestionRepository;
    this.enrolledCourseRepository = enrolledCourseRepository;
  }

  @Override
  public AnswerResponseDto create(
      Long enrolledCourseId, AnswerRequestDto requestDto, String email) {

    EnrolledCourse extractedEnrolledCourse =
        enrolledCourseRepository
            .findById(enrolledCourseId)
            .orElseThrow(IllegalArgumentException::new);

    PeersQuestion extractedPeersQuestion =
        peersQuestionRepository
            .findById(requestDto.getQuestionId())
            .orElseThrow(IllegalArgumentException::new);

    checkingDataConsistency(
        extractedPeersQuestion, extractedEnrolledCourse, email, requestDto.getQuestionId());

    return AnswerMapper.toAnswerDto(
        peersAnswerRepository.save(
            prepareAnswer(extractedEnrolledCourse, requestDto, extractedPeersQuestion)));
  }

  @Override
  public PeersAnswer findAnswerById(Long answerId) {
    return peersAnswerRepository.findById(answerId).orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public Page<PeersAnswer> findAnswerByQuestionId(Long questionId, Pageable pageable) {
    return peersAnswerRepository.findPeersAnswerByPeersQuestionId(questionId, pageable);
    }

  private PeersAnswer prepareAnswer(
      EnrolledCourse enrolledCourse, AnswerRequestDto requestDto, PeersQuestion peersQuestion) {

    PeersAnswer peersAnswer = new PeersAnswer();
    peersAnswer.setText(requestDto.getText());
    peersAnswer.setDiscussionQuestion(peersQuestion);
    peersAnswer.setUser(enrolledCourse.getUser());
    peersAnswer.setCreatedDate(Instant.now());

    return peersAnswer;
  }

  private void checkingDataConsistency(
      PeersQuestion extractedPeersQuestion,
      EnrolledCourse enrolledCourse,
      String currentUserEmail,
      Long questionId) {

    String emailFromEnrolledCourse = enrolledCourse.getUser().getEmail();
    Long questionIdFromExtractedQuestion = extractedPeersQuestion.getId();
    Long courseIdFromExtractedQuestion = extractedPeersQuestion.getCourse().getId();

    if (!Objects.equals(emailFromEnrolledCourse, currentUserEmail)
        || !Objects.equals(questionIdFromExtractedQuestion, questionId)
        || !Objects.equals(enrolledCourse.getCourse().getId(), courseIdFromExtractedQuestion)) {

      throw new PermissionDeniedException("permission.denied");
    }
  }
}
