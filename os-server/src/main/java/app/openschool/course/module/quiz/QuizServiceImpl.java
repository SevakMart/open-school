package app.openschool.course.module.quiz;

import app.openschool.course.module.Module;
import app.openschool.course.module.ModuleRepository;
import app.openschool.course.module.quiz.api.dto.CreateQuizDto;
import app.openschool.course.module.quiz.api.mapper.QuizMapper;
import java.util.Optional;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class QuizServiceImpl implements QuizService {

  private final ModuleRepository moduleRepository;
  private final QuizRepository quizRepository;

  public QuizServiceImpl(ModuleRepository moduleRepository, QuizRepository quizRepository) {
    this.moduleRepository = moduleRepository;
    this.quizRepository = quizRepository;
  }

  @Override
  public Optional<Quiz> createQuiz(Long moduleId, CreateQuizDto createQuizDto) {
    return moduleRepository
        .findById(moduleId)
        .map(
            module -> {
              checkIfTheModuleBelongsToCurrentMentor(module);
              Quiz quiz = QuizMapper.createQuizDtoToQuiz(createQuizDto, module);
              return quizRepository.save(quiz);
            });
  }

  @Override
  public Boolean deleteQuiz(Long quizId) {
    return quizRepository
        .findById(quizId)
        .map(
            quiz -> {
              checkIfTheQuizBelongsToCurrentMentor(quiz);
              quizRepository.delete(quiz);
              return true;
            })
        .orElse(false);
  }

  private void checkIfTheQuizBelongsToCurrentMentor(Quiz quiz) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!quiz.getModule().getCourse().getMentor().getEmail().equals(username)) {
      throw new IllegalArgumentException();
    }
  }

  private void checkIfTheModuleBelongsToCurrentMentor(Module module) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!module.getCourse().getMentor().getEmail().equals(username)) {
      throw new IllegalArgumentException();
    }
  }
}
