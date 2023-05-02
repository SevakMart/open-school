package app.openschool.course.module.quiz.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ModifyQuizDataRequest {
  @Schema(description = "Quiz title", example = "Quiz about ArrayList ")
  private String title;

  @Schema(
      description = "Quiz description",
      example = "With the ArrayList quiz you must answer at least 70% of the questions")
  private String description;

  @Schema(description = "Quiz maximum grade", example = "7")
  private int maxGrade = -1;

  @Schema(description = "Quiz passing score", example = "7")
  private int passingScore = -1;

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
}
