package app.openschool.course.discussion.mentor.question;

import app.openschool.common.exceptionhandler.exception.PermissionDeniedException;
import app.openschool.course.CourseRepository;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.discussion.QuestionService;
import app.openschool.course.discussion.dto.MentorQuestionResponseDto;
import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import app.openschool.course.discussion.dto.basedto.IQuestion;import app.openschool.course.discussion.dto.basedto.ResponseDto;
import app.openschool.course.discussion.mapper.MentorQuestionMapper;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.time.Instant;
import java.util.Objects;
import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;import org.springframework.stereotype.Service;

@Service("discussionQuestionMentor")
public class MentorQuestionServiceImpl implements QuestionService {

  private final EnrolledCourseRepository enrolledCourseRepository;
  private final MentorQuestionRepository mentorQuestionRepository;

  public MentorQuestionServiceImpl(
      EnrolledCourseRepository enrolledCourseRepository,
      MentorQuestionRepository mentorQuestionRepository) {
    this.enrolledCourseRepository = enrolledCourseRepository;
    this.mentorQuestionRepository = mentorQuestionRepository;
  }

  @Override
  public MentorQuestionResponseDto create(
      Long enrolledCourseId, QuestionRequestDto requestDto, String email) {

    EnrolledCourse enrolledCourse =
        enrolledCourseRepository
            .findById(enrolledCourseId)
            .orElseThrow(IllegalArgumentException::new);

    checkingDataConsistency(enrolledCourse, email);

    MentorQuestion mentorQuestion = prepareMentorQuestion(requestDto, enrolledCourse);

    return MentorQuestionMapper.toResponseDto(mentorQuestionRepository.save(mentorQuestion));
  }


  @Override
  public MentorQuestion update(
      UpdateQuestionRequest request,
      Long questionId,
      Long enrolledCourseId,
      String currentUserEmail) {
    return new MentorQuestion();
  }

  @Override
  public void delete(Long questionId, Long enrolledCourseId, String currentUserEmail) {}

  ///////////////////////////////////////////////////////////////////////////////////////
  @Override
  public Page<MentorQuestionResponseDto> findQuestionByCourseId(Long enrolledCourseId, Pageable pageable) {
    Page<MentorQuestion> all = mentorQuestionRepository.findAll(pageable);

     Page<MentorQuestionResponseDto> dtos = MentorQuestionMapper.toQuestionDtoPage(all);
     return dtos;
  }
//////////////////////////////////////////////////////////////
  private MentorQuestion prepareMentorQuestion(
      QuestionRequestDto requestDto, EnrolledCourse enrolledCourse) {

    MentorQuestion mentorQuestion = new MentorQuestion();

    mentorQuestion.setText(requestDto.getText());
    mentorQuestion.setMentor(enrolledCourse.getCourse().getMentor());
    mentorQuestion.setUser(enrolledCourse.getUser());
    mentorQuestion.setCourse(enrolledCourse.getCourse());
    mentorQuestion.setCreatedDate(Instant.now());

    return mentorQuestion;
  }

  private void checkingDataConsistency(EnrolledCourse enrolledCourse, String currentUserEmail) {

    String emailFromEnrolledCourse = enrolledCourse.getUser().getEmail();

    if (!Objects.equals(emailFromEnrolledCourse, currentUserEmail)) {

      throw new PermissionDeniedException("permission.denied");
    }
  }
}
