package app.openschool.course.module.quiz.question.util;

import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.question.type.QuestionType;
import app.openschool.course.module.quiz.util.QuizGenerator;

public final class QuestionGenerator {

  private static final Long ID = 1L;
  private static final String QUESTION = "question";

  private QuestionGenerator() {}

  public static Question generateQuestion() {
    Question question = Question.getInstance();
    question.setId(ID);
    question.setQuestion(QUESTION);
    question.setQuestionType(QuestionType.isMultipleChoice());
    question.setQuiz(QuizGenerator.generateQuiz());
    return question;
  }
}
