package app.openschool.discussion;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.discussion.dto.DiscussionAnswerRequestDto;
import app.openschool.discussion.dto.DiscussionAnswerResponseDto;
import app.openschool.discussion.mapper.DiscussionAnswerMentorMapper;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.security.Principal;
import java.time.Instant;
import org.springframework.stereotype.Service;


@Service("discussionAnswerMentor")
public class DiscussionAnswerMentorServiceImpl implements DiscussionAnswerService {

  private final UserRepository userRepository;
  private final DiscussionAnswerMentorRepository discussionAnswerMentorRepository;
  private final DiscussionQuestionMentorRepository discussionQuestionMentorRepository;

  public DiscussionAnswerMentorServiceImpl(
      UserRepository userRepository,
      DiscussionAnswerMentorRepository discussionAnswerMentorRepository,
      DiscussionQuestionMentorRepository discussionQuestionMentorRepository) {
    this.userRepository = userRepository;
    this.discussionAnswerMentorRepository = discussionAnswerMentorRepository;
    this.discussionQuestionMentorRepository = discussionQuestionMentorRepository;
  }

  @Override
  public DiscussionAnswerResponseDto create(
      DiscussionAnswerRequestDto requestDto, Principal principal) {

    return DiscussionAnswerMentorMapper.toAnswerDto(
        discussionAnswerMentorRepository.save(creteAnswer(requestDto, principal)));
  }

  private DiscussionAnswerMentor creteAnswer(
      DiscussionAnswerRequestDto requestDto, Principal principal) {
    User userByEmail = userRepository.findUserByEmail(principal.getName());
    DiscussionQuestionMentor questionById =
        discussionQuestionMentorRepository
            .findById(requestDto.getQuestionId())
            .orElseThrow(IllegalAccessError::new);
    if (questionById.getCourse().getMentor().getId() != userByEmail.getId()) {
      throw new PermissionDeniedException("permission.denied");
    }
    DiscussionAnswerMentor discussionAnswer = new DiscussionAnswerMentor();
    discussionAnswer.setText(requestDto.getText());
    discussionAnswer.setDiscussionQuestionMentor(questionById);
    discussionAnswer.setUser(userByEmail);
    discussionAnswer.setCreatedDate(Instant.now());

    return discussionAnswer;
  }
}
