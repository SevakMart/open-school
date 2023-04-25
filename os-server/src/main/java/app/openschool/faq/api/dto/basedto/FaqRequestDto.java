package app.openschool.faq.api.dto.basedto;

import javax.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public abstract class FaqRequestDto {

  @NotBlank(message = "{argument.required}")
  @Length(max = 500, message = "{faq.length.max}")
  private String question;

  @NotBlank(message = "{argument.required}")
  @Length(max = 500, message = "{faq.length.max}")
  private String answer;

  protected FaqRequestDto() {}

  protected FaqRequestDto(String question, String answer) {
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
