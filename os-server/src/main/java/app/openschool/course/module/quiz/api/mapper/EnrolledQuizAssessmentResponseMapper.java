package app.openschool.course.module.quiz.api.mapper;

import app.openschool.course.module.quiz.api.dto.EnrolledQuizAssessmentResponseDto;

public final class EnrolledQuizAssessmentResponseMapper {

  private EnrolledQuizAssessmentResponseMapper() {}

  public static EnrolledQuizAssessmentResponseDto toEnrolledQuizAssessmentResponseDto(
      int rightAnswers, String assessmentResult, int passingScore, int maxGrade) {
    EnrolledQuizAssessmentResponseDto enrolledQuizAssessmentResponseDto =
        EnrolledQuizAssessmentResponseDto.getInstant();
    enrolledQuizAssessmentResponseDto.setRightAnswersCount(rightAnswers);
    enrolledQuizAssessmentResponseDto.setAssessmentResult(assessmentResult);
    enrolledQuizAssessmentResponseDto.setMaxGrade(maxGrade);
    enrolledQuizAssessmentResponseDto.setPassingScore(passingScore);
    return enrolledQuizAssessmentResponseDto;
  }
}
