package app.openschool.course.module.quiz.question;

import app.openschool.course.module.quiz.api.dto.QuizDto;
import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import app.openschool.course.module.quiz.question.api.dto.QuestionDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionService {

  boolean deleteQuestion(Long questionId);

  boolean updateQuestion(Long questionId, CreateQuestionDto createQuestionDto);

  Page<QuestionDto> findAllByQuizId(Long id, Pageable pageable);
}
