package app.openschool.course.module.quiz.question.api.mapper;

import app.openschool.course.module.quiz.Quiz;
import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.question.answer.AnswerOption;
import app.openschool.course.module.quiz.question.answer.api.dto.CreateAnswerOptionDto;
import app.openschool.course.module.quiz.question.answer.api.mapper.AnswerMapper;
import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import app.openschool.course.module.quiz.question.api.dto.QuestionDto;
import app.openschool.course.module.quiz.question.type.QuestionType;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public final class QuestionMapper {

  private static final String MULTIPLE_CHOICE = "MULTIPLE_CHOICE";

  private QuestionMapper() {}

  public static Question createQuestionDtoToQuestion(
      CreateQuestionDto createQuestionDto, Quiz quiz) {
    Question question = Question.getInstance();
    question.setQuestion(createQuestionDto.getQuestion());
    question.setRightAnswersCount(createQuestionDto.getRightAnswersCount());
    question.setAnswerOptions(
        AnswerMapper.toAnswerOptionSet(createQuestionDto.getAnswerOptions(), question));
    question.setQuestionType(createQuestionType(createQuestionDto.getQuestionType()));
    question.setQuiz(quiz);
    return question;
  }

  public static Page<QuestionDto> toQuestionDtoPage(Page<Question> questionPage) {

    List<QuestionDto> collect =
        questionPage.toList().stream()
            .map(QuestionMapper::toQuestionDto)
            .collect(Collectors.toList());
    return new PageImpl<>(collect, questionPage.getPageable(), questionPage.getTotalElements());
  }

  public static QuestionDto toQuestionDto(Question question) {
    QuestionDto questionDto = new QuestionDto();
    questionDto.setId(question.getId());
    questionDto.setQuestion(question.getQuestion());
    questionDto.setQuestionType(question.getQuestionType().getType());
    questionDto.setAnswerOptions(AnswerMapper.toAnswerDtoSet(question.getAnswerOptions()));
    questionDto.setRightAnswersCount(question.getRightAnswersCount());
    return questionDto;
  }

  private static QuestionType createQuestionType(String questionType) {
    return questionType.equals(MULTIPLE_CHOICE)
        ? QuestionType.isMultipleChoice()
        : QuestionType.isMatching();
  }
}
