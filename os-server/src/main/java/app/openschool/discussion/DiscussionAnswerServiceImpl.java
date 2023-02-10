package app.openschool.discussion;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.discussion.dto.DiscussionAnswerRequestDto;
import app.openschool.discussion.dto.DiscussionAnswerResponseDto;
import app.openschool.discussion.mapper.DiscussionAnswerMapper;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.security.Principal;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service("discussionAnswer")
public class DiscussionAnswerServiceImpl implements DiscussionAnswerService {
  private final DiscussionAnswerRepository discussionAnswerRepository;
  private final DiscussionQuestionRepository discussionQuestionRepository;
  private final UserRepository userRepository;
  private final EnrolledCourseRepository enrolledCourseRepository;

  public DiscussionAnswerServiceImpl(
      DiscussionAnswerRepository discussionAnswerRepository,
      DiscussionQuestionRepository discussionQuestionRepository,
      UserRepository userRepository,
      EnrolledCourseRepository enrolledCourseRepository) {
    this.discussionAnswerRepository = discussionAnswerRepository;
    this.discussionQuestionRepository = discussionQuestionRepository;
    this.userRepository = userRepository;
    this.enrolledCourseRepository = enrolledCourseRepository;
  }

  @Override
  public DiscussionAnswerResponseDto create(
      DiscussionAnswerRequestDto requestDto, Principal principal) {
    DiscussionQuestion discussionQuestion =
        discussionQuestionRepository
            .findById(requestDto.getQuestionId())
            .orElseThrow(IllegalArgumentException::new);
    EnrolledCourse enrolledCourse =
        enrolledCourseRepository
            .findByCourseId(discussionQuestion.getCourse().getId())
            .orElseThrow(IllegalArgumentException::new);
    if (!enrolledCourse.getUser().getEmail().equals(principal.getName())) {
      throw new PermissionDeniedException("permission.denied");
    }
    return DiscussionAnswerMapper.toAnswerDto(
        discussionAnswerRepository.save(creteAnswer(requestDto, principal, discussionQuestion)));
  }

  private DiscussionAnswer creteAnswer(
      DiscussionAnswerRequestDto requestDto,
      Principal principal,
      DiscussionQuestion discussionQuestion) {
    User userByEmail = userRepository.findUserByEmail(principal.getName());
    DiscussionAnswer discussionAnswer = new DiscussionAnswer();
    discussionAnswer.setText(requestDto.getText());
    discussionAnswer.setDiscussionQuestion(discussionQuestion);
    discussionAnswer.setUser(userByEmail);
    discussionAnswer.setCreatedDate(Instant.now());

    return discussionAnswer;
  }
}
