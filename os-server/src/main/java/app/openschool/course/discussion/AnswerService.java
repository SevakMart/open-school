package app.openschool.course.discussion;

import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.AnswerResponseDto;import app.openschool.course.discussion.peers.answer.PeersAnswer;import org.springframework.data.domain.Page;import org.springframework.data.domain.Pageable;

public interface AnswerService {

  AnswerResponseDto create(Long enrolledCourseId, AnswerRequestDto requestDto, String email);

  PeersAnswer findAnswerById(Long answerId);

  Page<PeersAnswer>findAnswerByQuestionId(Long questionId, Pageable pageable);
}
