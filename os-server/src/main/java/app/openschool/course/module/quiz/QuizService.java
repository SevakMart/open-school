package app.openschool.course.module.quiz;

import app.openschool.course.module.quiz.api.dto.CreateQuizDto;
import app.openschool.course.module.quiz.api.dto.EnrolledQuizAssessmentRequestDto;
import app.openschool.course.module.quiz.api.dto.EnrolledQuizAssessmentResponseDto;
import app.openschool.course.module.quiz.api.dto.ModifyQuizDataRequest;
import app.openschool.course.module.quiz.api.dto.QuizDto;
import java.util.Locale;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuizService {

  Optional<Quiz> createQuiz(Long moduleId, CreateQuizDto createQuizDto);

  Boolean deleteQuiz(Long quizId);

  QuizDto findById(Long id);

  Page<QuizDto> findAllByModuleId(Long id, Pageable pageable);

  Optional<EnrolledQuizAssessmentResponseDto> completeEnrolledQuiz(
      Long enrolledQuizId,
      EnrolledQuizAssessmentRequestDto enrolledQuizAssessmentRequestDto,
      Locale locale);

  Quiz updateQuiz(Long id, ModifyQuizDataRequest request);
}
