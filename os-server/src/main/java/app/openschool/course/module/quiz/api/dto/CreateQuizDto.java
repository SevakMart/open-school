package app.openschool.course.module.quiz.api.dto;

import app.openschool.course.module.quiz.question.api.dto.CreateQuestionDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class CreateQuizDto {

  @Schema(description = "Maximum possible grade", example = "10")
  private int maxGrade;

  @Schema(description = "The minimum score to pass quiz", example = "7")
  private int passingScore;

  @Schema(description = "Quiz title", example = "Quiz about ArrayList ")
  private String title;

  @Schema(
      description = "Quiz description",
      example = "With the ArrayList quiz you must answer at least 70% of the questions")
  private String description;

  @ArraySchema(schema = @Schema(implementation = CreateQuestionDto.class))
  private Set<CreateQuestionDto> questions;

  public static CreateQuizDto getInstance() {
    return new CreateQuizDto();
  }

  public int getMaxGrade() {
    return maxGrade;
  }

  public void setMaxGrade(int maxGrade) {
    this.maxGrade = maxGrade;
  }

  public int getPassingScore() {
    return passingScore;
  }

  public void setPassingScore(int passingScore) {
    this.passingScore = passingScore;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<CreateQuestionDto> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<CreateQuestionDto> questions) {
    this.questions = questions;
  }
}
