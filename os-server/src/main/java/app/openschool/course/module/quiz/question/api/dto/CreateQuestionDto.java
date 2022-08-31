package app.openschool.course.module.quiz.question.api.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class CreateQuestionDto {

  @Schema(
      description = "Regular quiz question or multiple choice question",
      example = "What is Java bytecode")
  private String question;

  @ArraySchema(schema = @Schema(implementation = CreateAnswerOptionDto.class))
  private Set<CreateAnswerOptionDto> answerOptions;

  @Schema(
      description = "One of quiz question type (MATCHING or MULTIPLE_CHOICE)",
      example = "MULTIPLE_CHOICE")
  private String questionType;

  public static CreateQuestionDto getInstance() {
    return new CreateQuestionDto();
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public Set<CreateAnswerOptionDto> getAnswerOptions() {
    return answerOptions;
  }

  public void setAnswerOptions(Set<CreateAnswerOptionDto> answerOptions) {
    this.answerOptions = answerOptions;
  }

  public String getQuestionType() {
    return questionType;
  }

  public void setQuestionType(String questionType) {
    this.questionType = questionType;
  }
}
