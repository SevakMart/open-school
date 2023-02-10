package app.openschool.discussion;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.discussion.dto.DiscussionQuestionRequestDto;
import app.openschool.discussion.dto.DiscussionQuestionResponseDto;
import app.openschool.discussion.mapper.DiscussionQuestionMentorMapper;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;

@Service("discussionQuestionMentor")
public class DiscussionQuestionMentorServiceImpl implements DiscussionQuestionService {

  private final UserRepository userRepository;
  private final EnrolledCourseRepository enrolledCourseRepository;
  private final CourseRepository courseRepository;
  private final DiscussionQuestionMentorRepository discussionQuestionMentorRepository;

  public DiscussionQuestionMentorServiceImpl(
      UserRepository userRepository,
      EnrolledCourseRepository enrolledCourseRepository,
      CourseRepository courseRepository,
      DiscussionQuestionMentorRepository discussionQuestionMentorRepository) {
    this.userRepository = userRepository;
    this.enrolledCourseRepository = enrolledCourseRepository;
    this.courseRepository = courseRepository;
    this.discussionQuestionMentorRepository = discussionQuestionMentorRepository;
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

    return DiscussionQuestionMentorMapper.toResponseDto(
        discussionQuestionMentorRepository.save(creteQuestion(requestDto, principal)));
  }

  private DiscussionQuestionMentor creteQuestion(
      DiscussionQuestionRequestDto requestDto, Principal principal) {
    User userByEmail = userRepository.findUserByEmail(principal.getName());
    Course courseById =
        courseRepository.findById(requestDto.getCourseId()).orElseThrow(IllegalAccessError::new);
    DiscussionQuestionMentor discussionQuestionMentor = new DiscussionQuestionMentor();
    discussionQuestionMentor.setText(requestDto.getText());
    discussionQuestionMentor.setUser(userByEmail);
    discussionQuestionMentor.setCreatedDate(Instant.now());
    discussionQuestionMentor.setCourse(courseById);

    return discussionQuestionMentor;
  }
}
