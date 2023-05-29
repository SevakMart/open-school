package app.openschool.course.discussion;

import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.UpdateAnswerRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface AnswerService {

  Answer create(Long enrolledCourseId, AnswerRequestDto requestDto, String email);
  Answer update(
          UpdateAnswerRequest request,
          Long answerId,
          Long questionId,
          Long enrolledCourseId,
          String currentUserEmail);
  void delete(Long answerId, Long questionId, Long enrolledCourseId, String currentUserEmail);

  Answer findAnswerById(Long answerId);

  Page<? extends Answer> findAnswerByQuestionId(Long questionId, Pageable pageable);
}
