package app.openschool.course.module.quiz.util;

import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.quiz.EnrolledQuiz;
import app.openschool.course.module.quiz.status.QuizStatus;

public class EnrolledQuizGenerator {
  private EnrolledQuizGenerator() {}

  public static EnrolledQuiz generateEnrolledQuiz() {
    return new EnrolledQuiz(
        QuizStatus.isInProgress(), QuizGenerator.generateQuiz(), new EnrolledModule());
  }
}
