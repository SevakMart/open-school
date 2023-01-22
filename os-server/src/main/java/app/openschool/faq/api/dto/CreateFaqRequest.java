package app.openschool.faq.api.dto;

import javax.validation.constraints.NotNull;

public class CreateFaqRequest {

  @NotNull(message = "{argument.required}")
  private String question;

  @NotNull(message = "{argument.required}")
  private String answer;

  @NotNull(message = "{argument.required}")
  private Long courseId;

  public CreateFaqRequest() {}

  public CreateFaqRequest(String question, String answer, Long courseId) {
    this.question = question;
    this.answer = answer;
    this.courseId = courseId;
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

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }
}
