package app.openschool.course.module.quiz.api.dto;

import app.openschool.course.module.quiz.question.api.dto.QuestionDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class QuizDto {

  @Schema(description = "Question id", example = "1")
  private Long id;

  @Schema(description = "Id of the module to which the quiz belongs", example = "1")
  private Long moduleId;

  @Schema(description = "Maximum possible grade", example = "10")
  private int maxGrade;

  @Schema(description = "The student score", example = "9")
  private int studentGrade;

  @Schema(description = "The minimum score to pass quiz", example = "7")
  private int passingScore;

  @Schema(description = "The quiz current status", example = "IN_PROGRESS")
  private String quizStatus;

  @ArraySchema(schema = @Schema(implementation = QuestionDto.class))
  private Set<QuestionDto> questions;

  public static QuizDto getInstance() {
    return new QuizDto();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getModuleId() {
    return moduleId;
  }

  public void setModuleId(Long moduleId) {
    this.moduleId = moduleId;
  }

  public int getMaxGrade() {
    return maxGrade;
  }

  public void setMaxGrade(int maxGrade) {
    this.maxGrade = maxGrade;
  }

  public int getStudentGrade() {
    return studentGrade;
  }

  public void setStudentGrade(int studentGrade) {
    this.studentGrade = studentGrade;
  }

  public int getPassingScore() {
    return passingScore;
  }

  public void setPassingScore(int passingScore) {
    this.passingScore = passingScore;
  }

  public String getQuizStatus() {
    return quizStatus;
  }

  public void setQuizStatus(String quizStatus) {
    this.quizStatus = quizStatus;
  }

  public Set<QuestionDto> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<QuestionDto> questions) {
    this.questions = questions;
  }
}