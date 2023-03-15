package app.openschool.course.discussion.dto.basedto;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public abstract class RequestDto {

  @NotNull(message = "{argument.required}")
  @Length(max = 500, message = "{title.long}")
  private String text;

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
