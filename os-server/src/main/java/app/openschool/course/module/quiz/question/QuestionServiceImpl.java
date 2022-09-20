package app.openschool.course.module.quiz.question;

import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import app.openschool.course.module.quiz.question.api.mapper.QuestionMapper;
import org.springframework.security.core.context.SecurityContextHolder;
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
              checkIfQuestionBelongsToCurrentMentor(question);
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
              checkIfQuestionBelongsToCurrentMentor(question);
              Question updatedQuestion =
                  QuestionMapper.createQuestionDtoToQuestion(createQuestionDto, question.getQuiz());
              updatedQuestion.setId(question.getId());
              questionRepository.save(updatedQuestion);
              return true;
            })
        .orElse(false);
  }

  private void checkIfQuestionBelongsToCurrentMentor(Question question) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!question.getQuiz().getModule().getCourse().getMentor().getEmail().equals(username)) {
      throw new IllegalArgumentException();
    }
  }
}
