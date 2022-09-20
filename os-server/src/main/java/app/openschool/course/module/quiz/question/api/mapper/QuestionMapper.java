package app.openschool.course.module.quiz.question.api.mapper;

import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.question.answer.AnswerOption;
import app.openschool.course.module.quiz.question.answer.api.dto.CreateAnswerOptionDto;
import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import app.openschool.course.module.quiz.question.type.QuestionType;
import java.util.Set;
import java.util.stream.Collectors;

public final class QuestionMapper {

  private static final String MULTIPLE_CHOICE = "MULTIPLE_CHOICE";

  private QuestionMapper() {}

  public static Question createQuestionDtoToQuestion(
      CreateQuestionDto createQuestionDto, Quiz quiz) {
    Question question = Question.getInstance();
    question.setQuestion(createQuestionDto.getQuestion());
    question.setRightAnswersCount(createQuestionDto.getRightAnswersCount());
    question.setAnswerOptions(
        createAnswerOptionDtoToAnswerOptions(createQuestionDto.getAnswerOptions(), question));
    question.setQuestionType(createQuestionType(createQuestionDto.getQuestionType()));
    question.setQuiz(quiz);
    return question;
  }

  private static QuestionType createQuestionType(String questionType) {
    return questionType.equals(MULTIPLE_CHOICE)
        ? QuestionType.isMultipleChoice()
        : QuestionType.isMatching();
  }

  private static Set<AnswerOption> createAnswerOptionDtoToAnswerOptions(
      Set<CreateAnswerOptionDto> answerOptions, Question question) {
    return answerOptions.stream()
        .map(
            createAnswerOptionDto -> {
              AnswerOption answerOption = AnswerOption.getInstance();
              answerOption.setAnswerOption(createAnswerOptionDto.getAnswerOption());
              answerOption.setRightAnswer(createAnswerOptionDto.getRightAnswer());
              answerOption.setQuestion(question);
              return answerOption;
            })
        .collect(Collectors.toSet());
  }
}
