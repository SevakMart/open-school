package app.openschool.common.response;

import io.swagger.v3.oas.annotations.media.Schema;

public class ResponseMessage {

  @Schema(description = "Response message", example = "message")
  private final String message;

  public ResponseMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
