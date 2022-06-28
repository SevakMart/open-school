package app.openschool.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserRegistrationResponse {

  @Schema(description = "User Id", example = "1")
  private Long userId;

  public UserRegistrationResponse() {}

  public UserRegistrationResponse(Long userId) {
    this.userId = userId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }
}
