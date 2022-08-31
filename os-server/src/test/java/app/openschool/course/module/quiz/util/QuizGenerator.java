package app.openschool.course.module.quiz.util;

import app.openschool.course.module.Module;
import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.QuizStatus;
import app.openschool.course.module.quiz.question.AnswerOption;
import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.question.QuestionType;
import java.util.Set;

public final class QuizGenerator {

  private QuizGenerator() {}

  private static final Long ID = 1L;
  private static final int MAX_GRADE = 10;
  private static final int PASSING_SCORE = 7;
  private static final int STUDENT_GRADE = 9;
  private static final String QUESTION = "question";
  private static final String ANSWER_OPTION = "answerOption";
  private static final Boolean IS_RIGHT_ANSWER = true;

  public static Quiz generateQuiz() {
    Quiz quiz = Quiz.getInstance();
    quiz.setId(ID);
    quiz.setModule(new Module(ID));
    quiz.setMaxGrade(MAX_GRADE);
    quiz.setPassingScore(PASSING_SCORE);
    quiz.setStudentGrade(STUDENT_GRADE);
    quiz.setQuizStatus(QuizStatus.isInitial());
    quiz.setQuestions(generateQuestionSet());
    return quiz;
  }

  private static Set<Question> generateQuestionSet() {
    Question question = Question.getInstance();
    question.setId(ID);
    question.setQuestion(QUESTION);
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
