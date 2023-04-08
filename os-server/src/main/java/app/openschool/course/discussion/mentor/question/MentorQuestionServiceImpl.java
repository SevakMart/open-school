package app.openschool.course.discussion.mentor.question;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.CourseRepository;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.QuestionService;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import app.openschool.course.discussion.mapper.MentorQuestionMapper;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.time.Instant;
import org.springframework.stereotype.Service;

@Service("discussionQuestionMentor")
public class MentorQuestionServiceImpl implements QuestionService {

  // ToDo this class and the components used in it will be changed in the future
  private final UserRepository userRepository;
  private final EnrolledCourseRepository enrolledCourseRepository;
  private final CourseRepository courseRepository;
  private final MentorQuestionRepository mentorQuestionRepository;

  public MentorQuestionServiceImpl(
      UserRepository userRepository,
      EnrolledCourseRepository enrolledCourseRepository,
      CourseRepository courseRepository,
      MentorQuestionRepository mentorQuestionRepository) {
    this.userRepository = userRepository;
    this.enrolledCourseRepository = enrolledCourseRepository;
    this.courseRepository = courseRepository;
    this.mentorQuestionRepository = mentorQuestionRepository;
  }

  @Override
  public QuestionResponseDto create(
      Long enrolledCourseId, QuestionRequestDto requestDto, String email) {
    User user = userRepository.findUserByEmail(email);
    EnrolledCourse enrolledCourse =
        enrolledCourseRepository
            .findById(enrolledCourseId)
            .orElseThrow(IllegalArgumentException::new);
    if (enrolledCourse.getUser().getId() != user.getId()) {
      throw new PermissionDeniedException("permission.denied");
    }

    return MentorQuestionMapper.toResponseDto(
        mentorQuestionRepository.save(creteQuestion(requestDto, email)));
  }

  @Override
  public PeersQuestion update(
      UpdateQuestionRequest request,
      Long questionId,
      Long enrolledCourseId,
      String currentUserEmail) {
    return null;
  }

  @Override
  public void delete(Long questionId, Long enrolledCourseId, String currentUserEmail) {}

  private MentorQuestion creteQuestion(QuestionRequestDto requestDto, String email) {
    User userByEmail = userRepository.findUserByEmail(email);
    //    Course courseById =
    // courseRepository.findById(requestDto.getCourseId()).orElseThrow(IllegalAccessError::new);
    MentorQuestion mentorQuestion = new MentorQuestion();
    mentorQuestion.setText(requestDto.getText());
    mentorQuestion.setUser(userByEmail);
    mentorQuestion.setCreatedDate(Instant.now());
    //    mentorQuestion.setCourse(courseById);

    return mentorQuestion;
  }
}
