package app.openschool.discussion;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.discussion.dto.DiscussionQuestionRequestDto;
import app.openschool.discussion.dto.DiscussionQuestionResponseDto;
import app.openschool.discussion.mapper.DiscussionQuestionMapper;
import app.openschool.user.User;
import app.openschool.user.UserRepository;

import java.security.Principal;
import java.time.Instant;

import org.springframework.stereotype.Service;

@Service("discussionQuestion")
public class DiscussionQuestionServiceImpl implements DiscussionQuestionService {

  private final DiscussionQuestionRepository discussionQuestionRepository;
  private final CourseRepository courseRepository;
  private final UserRepository userRepository;
  private final EnrolledCourseRepository enrolledCourseRepository;

  public DiscussionQuestionServiceImpl(
      DiscussionQuestionRepository discussionQuestionRepository,
      CourseRepository courseRepository,
      UserRepository userRepository,
      EnrolledCourseRepository enrolledCourseRepository) {
    this.discussionQuestionRepository = discussionQuestionRepository;
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
    this.enrolledCourseRepository = enrolledCourseRepository;
  }

  @Override
  public DiscussionQuestionResponseDto create(
      DiscussionQuestionRequestDto requestDto, Principal principal) {
    User user = userRepository.findUserByEmail(principal.getName());
    EnrolledCourse enrolledCourse =
        enrolledCourseRepository
            .findByCourseId(requestDto.getCourseId())
            .orElseThrow(IllegalArgumentException::new);
    if (enrolledCourse.getUser().getId() != user.getId()) {
      throw new PermissionDeniedException("permission.denied");
    }
    return DiscussionQuestionMapper.toResponseDto(
        discussionQuestionRepository.save(creteQuestion(requestDto, principal)));
  }

  private DiscussionQuestion creteQuestion(
      DiscussionQuestionRequestDto requestDto, Principal principal) {
    User userByEmail = userRepository.findUserByEmail(principal.getName());
    Course courseById =
        courseRepository
            .findById(requestDto.getCourseId())
            .orElseThrow(IllegalArgumentException::new);
    DiscussionQuestion discussionQuestion = new DiscussionQuestion();
    discussionQuestion.setText(requestDto.getText());
    discussionQuestion.setUser(userByEmail);
    discussionQuestion.setCreatedDate(Instant.now());
    discussionQuestion.setCourse(courseById);
    return discussionQuestion;
  }
}
