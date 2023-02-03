package app.openschool.discussion.api;

import app.openschool.discussion.api.dto.DiscussionAnswerRequestDto;
import app.openschool.discussion.api.dto.DiscussionAnswerResponseDto;
import app.openschool.discussion.api.mapper.DiscussionAnswerMapper;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.security.Principal;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class DiscussionAnswerServiceImpl implements DiscussionAnswerService {
  private final DiscussionAnswerRepository discussionAnswerRepository;
  private final DiscussionQuestionRepository discussionQuestionRepository;
  private final UserRepository userRepository;

  public DiscussionAnswerServiceImpl(
          DiscussionAnswerRepository discussionAnswerRepository, DiscussionQuestionRepository discussionQuestionRepository, UserRepository userRepository) {
    this.discussionAnswerRepository = discussionAnswerRepository;
    this.discussionQuestionRepository = discussionQuestionRepository;
    this.userRepository = userRepository;
  }

  @Override
  public DiscussionAnswerResponseDto create(
      DiscussionAnswerRequestDto requestDto, Principal principal) {

    return DiscussionAnswerMapper.toAnswerDto(
        discussionAnswerRepository.save(creteAnswer(requestDto, principal)));
  }

  @Override
  public DiscussionAnswerResponseDto update(Long answerId, DiscussionAnswerRequestDto requestDto) {
    DiscussionAnswer answerById =
        discussionAnswerRepository.findById(answerId).orElseThrow(IllegalAccessError::new);
    answerById.setText(requestDto.getText());
    return DiscussionAnswerMapper.toAnswerDto(discussionAnswerRepository.save(answerById));
  }

  @Override
  public void delete(Long id) {
    DiscussionAnswer answerById =
        discussionAnswerRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    discussionAnswerRepository.delete(answerById);
  }

  private DiscussionAnswer creteAnswer(DiscussionAnswerRequestDto requestDto, Principal principal) {
    User userByEmail = userRepository.findUserByEmail(principal.getName());
    DiscussionQuestion questionById = discussionQuestionRepository.findById(requestDto.getQuestionId()).orElseThrow(IllegalAccessError::new);
    DiscussionAnswer discussionAnswer = new DiscussionAnswer();
    discussionAnswer.setText(requestDto.getText());
    discussionAnswer.setDiscussionQuestion(questionById);
    discussionAnswer.setUser(userByEmail);
    discussionAnswer.setCreatedDate(Instant.now());

    return discussionAnswer;
  }
}
