package app.openschool.course.module.quiz.api.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;

public class QuestionWithChosenAnswerDto {

  @Schema(description = "Regular quiz question or multiple choice question id", example = "1")
  private Long questionId;

  @ArraySchema(schema = @Schema(description = "One or multiple chosen answer's ids", example = "2"))
  private List<Long> chosenAnswersIds;

  public static QuestionWithChosenAnswerDto getInstance() {
    return new QuestionWithChosenAnswerDto();
  }

  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }

  public List<Long> getChosenAnswersIds() {
    return chosenAnswersIds;
  }

  public void setChosenAnswersIds(List<Long> chosenAnswersIds) {
    this.chosenAnswersIds = chosenAnswersIds;
  }
}
