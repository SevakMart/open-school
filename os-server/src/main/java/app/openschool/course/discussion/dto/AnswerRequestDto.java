package app.openschool.course.discussion.dto;

import app.openschool.course.discussion.dto.basedto.RequestDto;
import javax.validation.constraints.NotNull;

public class AnswerRequestDto extends RequestDto {
  @NotNull(message = "{argument.required}")
  private Long questionId;

  public Long getQuestionId() {
    return questionId;
  }

  public void setQuestionId(Long questionId) {
    this.questionId = questionId;
  }
}
