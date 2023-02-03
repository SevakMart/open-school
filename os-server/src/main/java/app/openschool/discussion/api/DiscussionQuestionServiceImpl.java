package app.openschool.discussion.api;

import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.discussion.api.dto.DiscussionQuestionRequestDto;
import app.openschool.discussion.api.dto.DiscussionQuestionResponseDto;
import app.openschool.discussion.api.mapper.DiscussionQuestionMapper;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.security.Principal;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service
public class DiscussionQuestionServiceImpl implements DiscussionQuestionService {

  private final DiscussionQuestionRepository discussionQuestionRepository;
  private final CourseRepository courseRepository;
  private final UserRepository userRepository;

  public DiscussionQuestionServiceImpl(
      DiscussionQuestionRepository discussionQuestionRepository,
      CourseRepository courseRepository,
      UserRepository userRepository) {
    this.discussionQuestionRepository = discussionQuestionRepository;
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
  }

  @Override
  public DiscussionQuestionResponseDto findById(Long id) {
    return DiscussionQuestionMapper.toResponseDto(
        discussionQuestionRepository.findById(id).orElseThrow(IllegalArgumentException::new));
  }

  //  @Override
  //  public Page<DiscussionQuestionResponseDto> findQuestionByCourseId(Long id, Pageable pageable)
  // {
  //    return DiscussionQuestionMapper.toDiscussionQuestionPageDto(
  //        discussionQuestionRepository.findAllByCourseId(id, pageable));
  //  }

  @Override
  public DiscussionQuestionResponseDto create(
      DiscussionQuestionRequestDto requestDto, Principal principal) {
    return DiscussionQuestionMapper.toResponseDto(
        discussionQuestionRepository.save(creteQuestion(requestDto, principal)));
  }

  @Override
  public DiscussionQuestionResponseDto update(Long id, DiscussionQuestionRequestDto requestDto) {
    DiscussionQuestion questionById =
        discussionQuestionRepository.findById(id).orElseThrow(IllegalAccessError::new);
    questionById.setText(requestDto.getText());

    return DiscussionQuestionMapper.toResponseDto(discussionQuestionRepository.save(questionById));
  }

  @Override
  public void delete(Long id) {
    DiscussionQuestion byId =
        discussionQuestionRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    discussionQuestionRepository.delete(byId);
  }

  private Course findCourse(Long id) {
    return courseRepository.findById(id).orElseThrow(IllegalArgumentException::new);
  }

  private DiscussionQuestion creteQuestion(
      DiscussionQuestionRequestDto requestDto, Principal principal) {
    User userByEmail = userRepository.findUserByEmail(principal.getName());
    Course courseById =
        courseRepository.findById(requestDto.getCourseId()).orElseThrow(IllegalAccessError::new);
    DiscussionQuestion discussionQuestion = new DiscussionQuestion();
    discussionQuestion.setText(requestDto.getText());
    discussionQuestion.setUser(userByEmail);
    discussionQuestion.setCreatedDate(Instant.now());
    discussionQuestion.setCourse(courseById);

    return discussionQuestion;
  }
}
