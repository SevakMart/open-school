package app.openschool.faq.api.dto;

import app.openschool.faq.api.dto.basedto.FaqRequestDto;
import javax.validation.constraints.NotNull;

public class CreateFaqRequest extends FaqRequestDto {
  @NotNull(message = "{argument.required}")
  private Long courseId;

  public CreateFaqRequest() {}

  public CreateFaqRequest(String question, String answer, Long courseId) {
    super(question, answer);
    this.courseId = courseId;
  }

  public Long getCourseId() {
    return courseId;
  }

  public void setCourseId(Long courseId) {
    this.courseId = courseId;
  }
}
