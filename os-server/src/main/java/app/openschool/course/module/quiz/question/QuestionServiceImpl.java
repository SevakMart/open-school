package app.openschool.course.module.quiz.question;

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
    return questionRepository.findById(questionId).map(question -> {
      checkIfQuestionBelongsToCurrentMentor(question);
      questionRepository.delete(question);
      return true;
    }).orElse(false);

  }

  private void checkIfQuestionBelongsToCurrentMentor(Question question) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    if (!question.getQuiz().getModule().getCourse().getMentor().getEmail().equals(username)) {
      throw new IllegalArgumentException();
    }
  }
}
