package app.openschool.course.module.quiz.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;

public class EnrolledQuizAssessmentResponseDto {

  @Schema(description = "Right answers count", example = "6")
  private int rightAnswersCount;

  @Schema(description = "Assessment result", example = "FAILED")
  private String assessmentResult;

  @Schema(description = "The minimum score to pass quiz", example = "7")
  private int passingScore;

  @Schema(description = "Maximum possible grade", example = "10")
  private int maxGrade;

  public EnrolledQuizAssessmentResponseDto() {}

  public EnrolledQuizAssessmentResponseDto(
      int rightAnswersCount, String assessmentResult, int passingScore, int maxGrade) {
    this.rightAnswersCount = rightAnswersCount;
    this.assessmentResult = assessmentResult;
    this.passingScore = passingScore;
    this.maxGrade = maxGrade;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    EnrolledQuizAssessmentResponseDto that = (EnrolledQuizAssessmentResponseDto) o;
    return rightAnswersCount == that.rightAnswersCount
        && passingScore == that.passingScore
        && maxGrade == that.maxGrade
        && Objects.equals(assessmentResult, that.assessmentResult);
  }

  public static EnrolledQuizAssessmentResponseDto getInstant() {
    return new EnrolledQuizAssessmentResponseDto();
  }

  public int getRightAnswersCount() {
    return rightAnswersCount;
  }

  public void setRightAnswersCount(int rightAnswersCount) {
    this.rightAnswersCount = rightAnswersCount;
  }

  public String getAssessmentResult() {
    return assessmentResult;
  }

  public void setAssessmentResult(String assessmentResult) {
    this.assessmentResult = assessmentResult;
  }

  public int getPassingScore() {
    return passingScore;
  }

  public void setPassingScore(int passingScore) {
    this.passingScore = passingScore;
  }

  public int getMaxGrade() {
    return maxGrade;
  }

  public void setMaxGrade(int maxGrade) {
    this.maxGrade = maxGrade;
  }
}
