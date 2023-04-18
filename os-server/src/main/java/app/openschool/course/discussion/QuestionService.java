package app.openschool.course.discussion;

import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;import java.util.Optional;

public interface QuestionService {
  QuestionResponseDto create(Long enrolledCourseId, QuestionRequestDto requestDto, String email);

  PeersQuestion update(
      UpdateQuestionRequest request,
      Long questionId,
      Long enrolledCourseId,
      String currentUserEmail);

  void delete(Long questionId, Long enrolledCourseId, String currentUserEmail);

  Page<PeersQuestion> findQuestionByCourseId(Long enrolledCourseId, Pageable pageable);

  PeersQuestion findQuestionByIdAndEnrolledCourseId(Long enrolledCourseId, Long questionId);
}
