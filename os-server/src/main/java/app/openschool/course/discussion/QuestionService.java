package app.openschool.course.discussion;

import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.UpdateQuestionRequest;
import app.openschool.course.discussion.dto.basedto.IQuestion;
import app.openschool.course.discussion.dto.basedto.ResponseDto;
import app.openschool.course.discussion.peers.question.PeersQuestion;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

public interface QuestionService {
//  IQuestion create(
  <T extends ResponseDto> T create(
      Long enrolledCourseId, QuestionRequestDto requestDto, String email);

//  <T extends IQuestion> T update(
  IQuestion update(
      UpdateQuestionRequest request,
      Long questionId,
      Long enrolledCourseId,
      String currentUserEmail);

  void delete(Long questionId, Long enrolledCourseId, String currentUserEmail);

  <T extends ResponseDto> Page<T> findQuestionByCourseId(Long enrolledCourseId, Pageable pageable);
}
