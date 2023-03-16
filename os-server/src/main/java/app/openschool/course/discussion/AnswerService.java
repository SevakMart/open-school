package app.openschool.course.discussion;

import app.openschool.course.discussion.dto.AnswerRequestDto;
import app.openschool.course.discussion.dto.AnswerResponseDto;

public interface AnswerService {

  AnswerResponseDto create(Long enrolledCourseId, AnswerRequestDto requestDto, String email);
}