package app.openschool.course.module.quiz.util;

import app.openschool.course.module.quiz.api.dto.EnrolledQuizAssessmentRequestDto;
import app.openschool.course.module.quiz.api.dto.QuestionWithChosenAnswerDto;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class EnrolledQuizAssessmentRequestDtoGenerator {

  private static final long ID = 1L;

  private EnrolledQuizAssessmentRequestDtoGenerator() {}

  public static EnrolledQuizAssessmentRequestDto generateEnrolledQuizAssessmentRequestDto() {
    EnrolledQuizAssessmentRequestDto enrolledQuizAssessmentRequestDto =
        EnrolledQuizAssessmentRequestDto.getInstance();
    enrolledQuizAssessmentRequestDto.setQuestionWithChosenAnswerDtoSet(
        generateQuestionWithChosenAnswerDtoSet());
    return enrolledQuizAssessmentRequestDto;
  }

  private static Set<QuestionWithChosenAnswerDto> generateQuestionWithChosenAnswerDtoSet() {
    Set<QuestionWithChosenAnswerDto> questionWithChosenAnswerDtoSet = new HashSet<>();
    QuestionWithChosenAnswerDto questionWithChosenAnswerDto =
        QuestionWithChosenAnswerDto.getInstance();
    questionWithChosenAnswerDto.setQuestionId(ID);
    questionWithChosenAnswerDto.setChosenAnswersIds(List.of(ID));
    questionWithChosenAnswerDtoSet.add(questionWithChosenAnswerDto);
    return questionWithChosenAnswerDtoSet;
  }
}
