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
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service("discussionAnswer")
public class PeersAnswerServiceImpl implements AnswerService {
  private final PeersAnswerRepository peersAnswerRepository;
  private final PeersQuestionRepository peersQuestionRepository;
  private final UserRepository userRepository;
  private final EnrolledCourseRepository enrolledCourseRepository;

  public PeersAnswerServiceImpl(
      PeersAnswerRepository peersAnswerRepository,
      PeersQuestionRepository peersQuestionRepository,
      UserRepository userRepository,
      EnrolledCourseRepository enrolledCourseRepository) {
    this.peersAnswerRepository = peersAnswerRepository;
    this.peersQuestionRepository = peersQuestionRepository;
    this.userRepository = userRepository;
    this.enrolledCourseRepository = enrolledCourseRepository;
  }

  @Override
  public AnswerResponseDto create(AnswerRequestDto requestDto, String email) {
    PeersQuestion peersQuestion =
        peersQuestionRepository
            .findById(requestDto.getQuestionId())
            .orElseThrow(IllegalArgumentException::new);
    EnrolledCourse enrolledCourse =
        enrolledCourseRepository
            .findByCourseId(peersQuestion.getCourse().getId())
            .orElseThrow(IllegalArgumentException::new);
    if (!enrolledCourse.getUser().getEmail().equals(email)) {
      throw new PermissionDeniedException("permission.denied");
    }
    return AnswerMapper.toAnswerDto(
        peersAnswerRepository.save(creteAnswer(requestDto, email, peersQuestion)));
  }

  @Override
  public AnswerResponseDto update(Long id, String text) {
    PeersAnswer peersAnswer =
        peersAnswerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    peersAnswer.setText(text);
    return AnswerMapper.toAnswerDto(peersAnswerRepository.save(peersAnswer));
  }

  @Override
  public void delete(Long id) {
    PeersAnswer peersAnswer =
        peersAnswerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    peersAnswerRepository.delete(peersAnswer);
  }

  private PeersAnswer creteAnswer(
      AnswerRequestDto requestDto, String email, PeersQuestion peersQuestion) {
    User userByEmail = userRepository.findUserByEmail(email);
    PeersAnswer peersAnswer = new PeersAnswer();
    peersAnswer.setText(requestDto.getText());
    peersAnswer.setDiscussionQuestion(peersQuestion);
    peersAnswer.setUser(userByEmail);
    peersAnswer.setCreatedDate(Instant.now());

    return peersAnswer;
  }
}
