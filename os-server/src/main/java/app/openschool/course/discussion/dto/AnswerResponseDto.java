package app.openschool.course.discussion.dto;

import app.openschool.course.discussion.dto.basedto.ResponseDto;
import app.openschool.user.api.dto.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.Instant;

public class AnswerResponseDto extends ResponseDto {

  @Schema(description = "Answer", example = "Any answer")
  @Override
  public String getText() {
    return super.getText();
  }

  public AnswerResponseDto() {
    super();
  }

  public AnswerResponseDto(Long id, String text, UserDto userDto, Instant createdDate) {
    super(id, text, userDto, createdDate);
  }
}
