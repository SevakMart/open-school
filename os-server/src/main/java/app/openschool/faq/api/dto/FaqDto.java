package app.openschool.faq.api.dto;

import app.openschool.course.api.dto.CourseInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;

public class FaqDto {

  @Schema(description = "id", example = "1")
  private Long id;

  @Schema(description = "Question", example = "Any question")
  private String question;

  @Schema(description = "Answer", example = "Any answer")
  private String answer;

  private CourseInfoDto courseInfoDto;

  public FaqDto(Long id, String question, String answer, CourseInfoDto courseInfoDto) {
    this.id = id;
    this.question = question;
    this.answer = answer;
    this.courseInfoDto = courseInfoDto;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getQuestion() {
    return question;
  }

  public void setQuestion(String question) {
    this.question = question;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public CourseInfoDto getCourseDto() {
    return courseInfoDto;
  }

  public void setCourseDto(CourseInfoDto courseInfoDto) {
    this.courseInfoDto = courseInfoDto;
  }
}
