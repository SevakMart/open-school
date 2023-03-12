package app.openschool.course.discussion.peers.question;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.QuestionService;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.mapper.QuestionMapper;
import java.time.Instant;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service("discussionQuestion")
public class PeersQuestionServiceImpl implements QuestionService {

  private final PeersQuestionRepository peersQuestionRepository;
  private final EnrolledCourseRepository enrolledCourseRepository;

  public PeersQuestionServiceImpl(
      PeersQuestionRepository peersQuestionRepository,
      EnrolledCourseRepository enrolledCourseRepository) {
    this.peersQuestionRepository = peersQuestionRepository;
    this.enrolledCourseRepository = enrolledCourseRepository;
  }

  @Override
  public QuestionResponseDto create(
      Long enrolledCourseId, QuestionRequestDto requestDto, String email) {

    EnrolledCourse extractedEnrolledCourse =
        enrolledCourseRepository
            .findById(enrolledCourseId)
            .orElseThrow(IllegalArgumentException::new);

    checkingDataConsistency(extractedEnrolledCourse, email);

    return QuestionMapper.toResponseDto(
        peersQuestionRepository.save(prepareQuestion(requestDto, extractedEnrolledCourse)));
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
