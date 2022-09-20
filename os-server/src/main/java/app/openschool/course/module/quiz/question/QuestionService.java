package app.openschool.course.module.quiz.question;

import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;

public interface QuestionService {

  boolean deleteQuestion(Long questionId);

  boolean updateQuestion(Long questionId, CreateQuestionDto createQuestionDto);
}
