package app.openschool.course.module.quiz.question.answer.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class AnswerOptionDto {

  @Schema(description = "Answer option id", example = "1")
  private Long id;

  @Schema(description = "One of the answers to the quiz question", example = "static")
  private String answerOption;

  public static AnswerOptionDto getInstance() {
    return new AnswerOptionDto();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getAnswerOption() {
    return answerOption;
  }

  public void setAnswerOption(String answerOption) {
    this.answerOption = answerOption;
  }
}
