package app.openschool.course.module.quiz.question.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class CreateAnswerOptionDto {

  @Schema(
      description = "One of the answers to the quiz question",
      example = "Java bytecode is the instruction set for the Java Virtual Machine")
  private String answerOption;

  @Schema(description = "Indicates if the answer is correct or not", example = "true")
  private Boolean isRightAnswer;

  public static CreateAnswerOptionDto getInstance() {
    return new CreateAnswerOptionDto();
  }

  public String getAnswerOption() {
    return answerOption;
  }

  public void setAnswerOption(String answerOption) {
    this.answerOption = answerOption;
  }

  public Boolean getRightAnswer() {
    return isRightAnswer;
  }

  public void setRightAnswer(Boolean rightAnswer) {
    isRightAnswer = rightAnswer;
  }
}
