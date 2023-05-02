package app.openschool.course.module.quiz.question.util;

import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.question.answer.AnswerOption;
import app.openschool.course.module.quiz.question.api.dto.QuestionDto;
import app.openschool.course.module.quiz.question.api.mapper.QuestionMapper;
import app.openschool.course.module.quiz.question.type.QuestionType;
import app.openschool.course.module.quiz.util.QuizGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class QuestionGenerator {
  private static final Long ID = 1L;
  private static final String QUESTION = "question";

  private QuestionGenerator() {}

  public static Question generateQuestion() {
    Question question = Question.getInstance();
    question.setId(ID);
    question.setQuestion(QUESTION);
    question.setQuestionType(QuestionType.isMultipleChoice());
    question.setQuiz(QuizGenerator.generateQuiz());
    question.setAnswerOptions(generateAnswerOption());
    return question;
  }

  public static Page<QuestionDto> generateQuestionDtoPage() {
    List<QuestionDto> questionDtoList = new ArrayList<>();
    QuestionDto questionDto = QuestionMapper.toQuestionDto(generateQuestion());
    questionDtoList.add(questionDto);
    return new PageImpl<>(questionDtoList);
  }

  public static Page<Question> generateQuestionPage() {
    List<Question> questionList = new ArrayList<>();
    questionList.add(generateQuestion());
    return new PageImpl<>(questionList);
  }

  private static Set<AnswerOption> generateAnswerOption() {
    AnswerOption answerOption = new AnswerOption();
    answerOption.setAnswerOption("answer 1");
    answerOption.setRightAnswer(true);

    return Set.of(answerOption);
  }
}
