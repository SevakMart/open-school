package app.openschool.course.discussion.mentor.answer;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.discussion.AnswerService;
import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.course.discussion.mapper.MentorAnswerMapper;
import app.openschool.course.discussion.mentor.question.MentorQuestion;
import app.openschool.course.discussion.mentor.question.MentorQuestionRepository;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service("discussionAnswerMentor")
public class MentorAnswerServiceImpl implements AnswerService {

  private final UserRepository userRepository;
  private final MentorAnswerRepository mentorAnswerRepository;
  private final MentorQuestionRepository mentorQuestionRepository;

  public MentorAnswerServiceImpl(
      UserRepository userRepository,
      MentorAnswerRepository mentorAnswerRepository,
      MentorQuestionRepository mentorQuestionRepository) {
    this.userRepository = userRepository;
    this.mentorAnswerRepository = mentorAnswerRepository;
    this.mentorQuestionRepository = mentorQuestionRepository;
  }

  @Override
  public AnswerResponseDto create(AnswerRequestDto requestDto, String email) {

    return MentorAnswerMapper.toAnswerDto(
        mentorAnswerRepository.save(creteAnswer(requestDto, email)));
  }


  private MentorAnswer creteAnswer(AnswerRequestDto requestDto, String email) {
    User userByEmail = userRepository.findUserByEmail(email);
    MentorQuestion questionById =
        mentorQuestionRepository
            .findById(requestDto.getQuestionId())
            .orElseThrow(IllegalAccessError::new);
    if (questionById.getCourse().getMentor().getId() != userByEmail.getId()) {
      throw new PermissionDeniedException("permission.denied");
    }
    MentorAnswer discussionAnswer = new MentorAnswer();
    discussionAnswer.setText(requestDto.getText());
    discussionAnswer.setDiscussionQuestionMentor(questionById);
    discussionAnswer.setUser(userByEmail);
    discussionAnswer.setCreatedDate(Instant.now());

    return discussionAnswer;
  }
}
