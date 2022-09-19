package app.openschool.course.module.quiz.api.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class EnrolledQuizAssessmentRequestDto {

  @ArraySchema(schema = @Schema(implementation = QuestionWithChosenAnswerDto.class))
  private Set<QuestionWithChosenAnswerDto> questionWithChosenAnswerDtoSet;

  public static EnrolledQuizAssessmentRequestDto getInstance() {
    return new EnrolledQuizAssessmentRequestDto();
  }

  public Set<QuestionWithChosenAnswerDto> getQuestionWithChosenAnswerDtoSet() {
    return questionWithChosenAnswerDtoSet;
  }

  public void setQuestionWithChosenAnswerDtoSet(
      Set<QuestionWithChosenAnswerDto> questionWithChosenAnswerDtoSet) {
    this.questionWithChosenAnswerDtoSet = questionWithChosenAnswerDtoSet;
  }
}
