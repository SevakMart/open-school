package app.openschool.course.module.quiz.question;

import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import app.openschool.course.module.quiz.question.api.dto.QuestionDto;
import app.openschool.course.module.quiz.question.api.mapper.QuestionMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class QuestionServiceImpl implements QuestionService {

  private final QuestionRepository questionRepository;

  public QuestionServiceImpl(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  @Override
  public boolean deleteQuestion(Long questionId) {
    return questionRepository
        .findById(questionId)
        .map(
            question -> {
              questionRepository.delete(question);
              return true;
            })
        .orElse(false);
  }

  @Override
  public boolean updateQuestion(Long questionId, CreateQuestionDto createQuestionDto) {
    return questionRepository
        .findById(questionId)
        .map(
            question -> {
              Question updatedQuestion =
                  QuestionMapper.createQuestionDtoToQuestion(createQuestionDto, question.getQuiz());
              updatedQuestion.setId(question.getId());
              questionRepository.save(updatedQuestion);
              return true;
            })
        .orElse(false);
  }

  @Override
  public Page<QuestionDto> findAllByQuizId(Long id, Pageable pageable) {
    return QuestionMapper.toQuestionDtoPage(
        questionRepository
            .findAllQuestionsByQuizId(id, pageable)
            .orElseThrow(IllegalArgumentException::new));
  }
}
