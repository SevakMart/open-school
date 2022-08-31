package app.openschool.course.module.quiz;

import app.openschool.course.module.quiz.api.dto.CreateQuizDto;
import java.util.Optional;

public interface QuizService {

  Optional<Quiz> createQuiz(Long moduleId, CreateQuizDto createQuizDto);
}
