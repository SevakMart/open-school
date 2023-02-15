package app.openschool.course.discussion.peers.question;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.QuestionService;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.mapper.QuestionMapper;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service("discussionQuestion")
public class PeersQuestionServiceImpl implements QuestionService {

  private final PeersQuestionRepository peersQuestionRepository;
  private final CourseRepository courseRepository;
  private final UserRepository userRepository;
  private final EnrolledCourseRepository enrolledCourseRepository;

  public PeersQuestionServiceImpl(
      PeersQuestionRepository peersQuestionRepository,
      CourseRepository courseRepository,
      UserRepository userRepository,
      EnrolledCourseRepository enrolledCourseRepository) {
    this.peersQuestionRepository = peersQuestionRepository;
    this.courseRepository = courseRepository;
    this.userRepository = userRepository;
    this.enrolledCourseRepository = enrolledCourseRepository;
  }

  @Override
  public QuestionResponseDto create(QuestionRequestDto requestDto, String email) {
    User user = userRepository.findUserByEmail(email);
    EnrolledCourse enrolledCourse =
        enrolledCourseRepository
            .findByCourseId(requestDto.getCourseId())
            .orElseThrow(IllegalArgumentException::new);
    if (enrolledCourse.getUser().getId() != user.getId()) {
      throw new PermissionDeniedException("permission.denied");
    }
    return QuestionMapper.toResponseDto(
        peersQuestionRepository.save(creteQuestion(requestDto, email)));
  }

  private PeersQuestion creteQuestion(QuestionRequestDto requestDto, String email) {
    User userByEmail = userRepository.findUserByEmail(email);
    Course courseById =
        courseRepository
            .findById(requestDto.getCourseId())
            .orElseThrow(IllegalArgumentException::new);
    PeersQuestion peersQuestion = new PeersQuestion();
    peersQuestion.setText(requestDto.getText());
    peersQuestion.setUser(userByEmail);
    peersQuestion.setCreatedDate(Instant.now());
    peersQuestion.setCourse(courseById);
    return peersQuestion;
  }
}
