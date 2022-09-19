package app.openschool.course.module.quiz.util;

import app.openschool.course.module.quiz.api.dto.EnrolledQuizAssessmentResponseDto;

public final class EnrolledQuizAssessmentResponseDtoGenerator {

  private static final int MAX_GRADE = 10;
  private static final int PASSING_SCORE = 7;
  private static final int RIGHT_ANSWER_COUNT = 1;

  private EnrolledQuizAssessmentResponseDtoGenerator() {}

  public static EnrolledQuizAssessmentResponseDto generateEnrolledQuizAssessmentResponseDto(
      String assessmentResult) {
    return new EnrolledQuizAssessmentResponseDto(
        RIGHT_ANSWER_COUNT, assessmentResult, PASSING_SCORE, MAX_GRADE);
  }
}
