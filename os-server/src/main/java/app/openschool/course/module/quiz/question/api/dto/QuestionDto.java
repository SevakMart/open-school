package app.openschool.course.module.quiz.question.api.dto;

import app.openschool.course.module.quiz.question.answer.api.dto.AnswerOptionDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class QuestionDto {

  @Schema(description = "Question id", example = "1")
  private Long id;

  @Schema(
      description = "Regular quiz question or multiple choice question",
      example = "Which of the following is a Java keyword")
  private String question;

  @Schema(description = "The right answers count", example = "3")
  private int rightAnswersCount;

  @Schema(
      description = "One of quiz question type (MATCHING or MULTIPLE_CHOICE)",
      example = "MULTIPLE_CHOICE")
  private String questionType;

  @ArraySchema(schema = @Schema(implementation = AnswerOptionDto.class))
  private Set<AnswerOptionDto> answerOptions;

  public static QuestionDto getInstance() {
    return new QuestionDto();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getQuestionType() {
    return questionType;
  }

  public void setQuestionType(String questionType) {
    this.questionType = questionType;
  }

  public Set<AnswerOptionDto> getAnswerOptions() {
    return answerOptions;
  }

  public void setAnswerOptions(Set<AnswerOptionDto> answerOptions) {
    this.answerOptions = answerOptions;
  }

  public int getRightAnswersCount() {
    return rightAnswersCount;
  }

  public void setRightAnswersCount(int rightAnswersCount) {
    this.rightAnswersCount = rightAnswersCount;
  }
}
