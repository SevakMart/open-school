package app.openschool.course.module.quiz.util;

import app.openschool.course.module.api.ModuleGenerator;
import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.api.dto.QuizDto;
import app.openschool.course.module.quiz.api.mapper.QuizMapper;
import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.question.answer.AnswerOption;
import app.openschool.course.module.quiz.question.type.QuestionType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class QuizGenerator {
  private static final Long ID = 1L;
  private static final int MAX_GRADE = 10;

  private static final String DESCRIPTION = "description";

  private static final String TITLE = "title";
  private static final int PASSING_SCORE = 7;
  private static final String QUESTION = "question";
  private static final String ANSWER_OPTION = "answerOption";
  private static final Boolean IS_RIGHT_ANSWER = true;
  private static final int RIGHT_ANSWER_COUNT = 1;

  private QuizGenerator() {}

  public static Quiz generateQuiz() {
    Quiz quiz = Quiz.getInstance();
    quiz.setId(ID);
    quiz.setTitle(TITLE);
    quiz.setDescription(DESCRIPTION);
    quiz.setModule(ModuleGenerator.generateModule());
    quiz.setMaxGrade(MAX_GRADE);
    quiz.setPassingScore(PASSING_SCORE);
    quiz.setQuestions(generateQuestionSet());
    return quiz;
  }

  public static Page<Quiz> generateQuizPage() {
    List<Quiz> quizList = new ArrayList<>();
    quizList.add(generateQuiz());
    return new PageImpl<>(quizList);
  }

  public static Page<QuizDto> generateQuizDtoPage() {
    List<QuizDto> quizDtoList = new ArrayList<>();
    QuizDto quizDto = QuizMapper.quizToQuizDto(generateQuiz());
    quizDtoList.add(quizDto);
    return new PageImpl<>(quizDtoList);
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
