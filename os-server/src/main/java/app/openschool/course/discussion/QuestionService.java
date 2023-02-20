package app.openschool.course.discussion;

import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.QuestionResponseDto;

public interface QuestionService {
  QuestionResponseDto create(QuestionRequestDto requestDto, String email);

  QuestionResponseDto update(Long id, String text);

  void delete(Long id);
}
