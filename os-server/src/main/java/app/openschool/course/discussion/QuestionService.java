package app.openschool.course.discussion;

import app.openschool.course.discussion.dto.QuestionRequestDto;
import app.openschool.course.discussion.dto.QuestionResponseDto;

public interface QuestionService {
  QuestionResponseDto create(Long enrolledCourseId, QuestionRequestDto requestDto, String email);
}
