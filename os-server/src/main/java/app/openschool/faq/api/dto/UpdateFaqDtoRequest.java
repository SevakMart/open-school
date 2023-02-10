package app.openschool.faq.api.dto;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class UpdateFaqDtoRequest {

  @NotBlank(message = "{argument.required}")
  @Length(max = 500, message = "{faq.length.max}")
  private String question;

  @NotBlank(message = "{argument.required}")
  @Length(max = 500, message = "{faq.length.max}")
  private String answer;

  public UpdateFaqDtoRequest() {}

  public UpdateFaqDtoRequest(String question, String answer) {
    this.question = question;
    this.answer = answer;
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
}
