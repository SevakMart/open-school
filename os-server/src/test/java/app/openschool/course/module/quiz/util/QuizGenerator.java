package app.openschool.course.module.quiz.util;

import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.question.answer.AnswerOption;
import app.openschool.course.module.quiz.question.type.QuestionType;
import app.openschool.course.module.util.ModuleGenerator;
import java.util.Set;

public final class QuizGenerator {

  private static final Long ID = 1L;
  private static final int MAX_GRADE = 10;
  private static final int PASSING_SCORE = 7;
  private static final String QUESTION = "question";
  private static final String ANSWER_OPTION = "answerOption";
  private static final Boolean IS_RIGHT_ANSWER = true;
  private static final int RIGHT_ANSWER_COUNT = 1;

  private QuizGenerator() {}

  public static Quiz generateQuiz() {
    Quiz quiz = Quiz.getInstance();
    quiz.setId(ID);
    quiz.setModule(ModuleGenerator.generateModule());
    quiz.setMaxGrade(MAX_GRADE);
    quiz.setPassingScore(PASSING_SCORE);
    quiz.setQuestions(generateQuestionSet());
    return quiz;
  }

  private static Set<Question> generateQuestionSet() {
    Question question = Question.getInstance();
    question.setId(ID);
    question.setQuestion(QUESTION);
    question.setRightAnswersCount(RIGHT_ANSWER_COUNT);
    question.setQuestionType(QuestionType.isMultipleChoice());
    question.setAnswerOptions(generateAnswerOptionSet());
    return Set.of(question);
  }

  private static Set<AnswerOption> generateAnswerOptionSet() {
    AnswerOption answerOption = AnswerOption.getInstance();
    answerOption.setId(ID);
    answerOption.setAnswerOption(ANSWER_OPTION);
    answerOption.setRightAnswer(IS_RIGHT_ANSWER);
    return Set.of(answerOption);
  }
}
