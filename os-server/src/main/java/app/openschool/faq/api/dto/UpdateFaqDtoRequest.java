package app.openschool.faq.api.dto;

import app.openschool.faq.api.dto.basedto.FaqRequestDto;

public class UpdateFaqDtoRequest extends FaqRequestDto {

  public UpdateFaqDtoRequest() {}

  public UpdateFaqDtoRequest(String question, String answer) {
    super(question, answer);
  }
}
