package app.openschool.course.module.quiz.util;

import app.openschool.course.module.quiz.api.dto.CreateQuizDto;
import app.openschool.course.module.quiz.api.dto.ModifyQuizDataRequest;
import app.openschool.course.module.quiz.api.dto.QuizDto;
import app.openschool.course.module.quiz.question.answer.api.dto.CreateAnswerOptionDto;
import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import app.openschool.course.module.quiz.question.api.dto.QuestionDto;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;



public class QuizDtoGenerator {
  private QuizDtoGenerator() {}

  private static final int MAX_GRADE = 10;
  private static final int PASSING_SCORE = 7;
  private static final String QUESTION = "question";

  private static final String DESCRIPTION = "description";

  private static final String TITLE = "title";
  private static final String MULTIPLE_CHOICE = "MULTIPLE_CHOICE";
  private static final String ANSWER_OPTION = "answerOption";
  private static final Boolean IS_RIGHT_ANSWER = true;

  public static CreateQuizDto generateCreateQuizDto() {
    CreateQuizDto createQuizDto = CreateQuizDto.getInstance();
    createQuizDto.setTitle(TITLE);
    createQuizDto.setDescription(DESCRIPTION);
    createQuizDto.setMaxGrade(MAX_GRADE);
    createQuizDto.setPassingScore(PASSING_SCORE);
    createQuizDto.setQuestions(generateCreateQuestionDtoSet());
    return createQuizDto;
  }

  public static Page<QuizDto> generateQuizDtoPage() {
    List<QuizDto> quizDtoList = new ArrayList<>();
    quizDtoList.add(generateQuizDto());
    return new PageImpl<>(quizDtoList);
  }

  public static QuizDto generateQuizDto() {
    QuizDto quizDto = new QuizDto();
    quizDto.setTitle(TITLE);
    quizDto.setDescription(DESCRIPTION);
    quizDto.setMaxGrade(MAX_GRADE);
    quizDto.setPassingScore(PASSING_SCORE);
    quizDto.setQuestions(generateQuestionDtoSet());
    return quizDto;
  }

  public static ModifyQuizDataRequest generateModifyQuizDataRequest() {
    ModifyQuizDataRequest request = new ModifyQuizDataRequest();
    request.setTitle(TITLE);
    request.setDescription(DESCRIPTION);
    request.setMaxGrade(MAX_GRADE);
    request.setPassingScore(PASSING_SCORE);
    return request;
  }

  private static Set<CreateQuestionDto> generateCreateQuestionDtoSet() {
    CreateQuestionDto createQuestionDto = CreateQuestionDto.getInstance();
    createQuestionDto.setQuestion(QUESTION);
    createQuestionDto.setQuestionType(MULTIPLE_CHOICE);
    createQuestionDto.setAnswerOptions(generateCreateAnswerOptionDtoSet());
    return Set.of(createQuestionDto);
  }

  private static Set<QuestionDto> generateQuestionDtoSet() {
    QuestionDto questionDto = QuestionDto.getInstance();
    questionDto.setQuestion(QUESTION);
    questionDto.setQuestionType(MULTIPLE_CHOICE);
    return Set.of(questionDto);
  }

  private static Set<CreateAnswerOptionDto> generateCreateAnswerOptionDtoSet() {
    CreateAnswerOptionDto createAnswerOptionDto = CreateAnswerOptionDto.getInstance();
    createAnswerOptionDto.setAnswerOption(ANSWER_OPTION);
    createAnswerOptionDto.setRightAnswer(IS_RIGHT_ANSWER);
    return Set.of(createAnswerOptionDto);
  }
}
