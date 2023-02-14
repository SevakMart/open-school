package app.openschool.course.module.quiz.api.dto;

import app.openschool.course.module.quiz.question.api.dto.QuestionDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class QuizDto {

  @Schema(description = "Quiz id", example = "1")
  private Long id;

  @Schema(description = "Id of the module to which the quiz belongs", example = "1")
  private Long moduleId;

  @Schema(description = "The title of the quiz", example = "title")
  private String title;

  @Schema(description = "The description of the quiz", example = "description")
  private String description;

  @Schema(description = "Maximum possible grade", example = "10")
  private int maxGrade;

  @Schema(description = "The minimum score to pass quiz", example = "7")
  private int passingScore;

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

  public Set<QuestionDto> getQuestions() {
    return questions;
  }

  public void setQuestions(Set<QuestionDto> questions) {
    this.questions = questions;
  }
}
