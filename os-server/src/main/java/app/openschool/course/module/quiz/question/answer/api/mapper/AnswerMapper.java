package app.openschool.course.module.quiz.question.answer.api.mapper;

import app.openschool.course.module.quiz.question.Question;
import app.openschool.course.module.quiz.question.answer.AnswerOption;
import app.openschool.course.module.quiz.question.answer.api.dto.AnswerOptionDto;
import app.openschool.course.module.quiz.question.answer.api.dto.CreateAnswerOptionDto;
import java.util.Set;
import java.util.stream.Collectors;

public class AnswerMapper {

  public static AnswerOptionDto toAnswerDto(AnswerOption answerOption) {
    AnswerOptionDto answerOptionDto = new AnswerOptionDto();
    answerOptionDto.setId(answerOptionDto.getId());
    answerOptionDto.setAnswerOption(answerOptionDto.getAnswerOption());

    return answerOptionDto;
  }

  public static AnswerOption toAnswerOption(
      CreateAnswerOptionDto answerOptionDto, Question question) {
    AnswerOption answerOption = new AnswerOption();
    answerOption.setQuestion(question);
    answerOption.setRightAnswer(answerOptionDto.getRightAnswer());
    answerOption.setAnswerOption(answerOptionDto.getAnswerOption());

    return answerOption;
  }

  public static Set<AnswerOptionDto> toAnswerDtoSet(Set<AnswerOption> answerOptions) {
    return answerOptions.stream().map(AnswerMapper::toAnswerDto).collect(Collectors.toSet());
  }

  public static Set<AnswerOption> toAnswerOptionSet(
      Set<CreateAnswerOptionDto> createAnswerOptionDtoSet, Question question) {
    return createAnswerOptionDtoSet.stream()
        .map(createAnswerOptionDto -> AnswerMapper.toAnswerOption(createAnswerOptionDto, question))
        .collect(Collectors.toSet());
  }
}
