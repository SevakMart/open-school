package app.openschool.course.discussion;

import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.course.discussion.peers.answer.PeersAnswer;

public interface AnswerService {

  AnswerResponseDto create(AnswerRequestDto requestDto, String email);

  AnswerResponseDto update(Long id, String text);

  void delete(Long id);
}
