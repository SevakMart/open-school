package app.openschool.course.module.quiz.api.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import app.openschool.course.module.quiz.api.dto.EnrolledQuizAssessmentResponseDto;
import org.junit.jupiter.api.Test;

public class EnrolledQuizAssessmentResponseMapperTest {
  @Test
  void toEnrolledQuizAssessmentResponseDto() {
    EnrolledQuizAssessmentResponseDto enrolledQuizAssessmentResponseDto =
        EnrolledQuizAssessmentResponseMapper.toEnrolledQuizAssessmentResponseDto(
            2, "COMPLETED", 2, 3);

    assertThat(enrolledQuizAssessmentResponseDto)
        .hasOnlyFields("rightAnswersCount", "assessmentResult", "passingScore", "maxGrade");
  }
}
