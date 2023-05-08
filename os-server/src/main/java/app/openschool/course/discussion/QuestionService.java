package app.openschool.course.discussion;

import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {
  Question create(
      Long enrolledCourseId, QuestionRequestDto requestDto, String email);

  Question update(
      UpdateQuestionRequest request,
      Long questionId,
      Long enrolledCourseId,
      String currentUserEmail);

  void delete(Long questionId, Long enrolledCourseId, String currentUserEmail);

  Page<? extends Question> findQuestionByCourseId(Long enrolledCourseId, Pageable pageable);

  Question findQuestionByIdAndEnrolledCourseId(Long enrolledCourseId, Long questionId);
}
