package app.openschool.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserLoginRequest {
  @Schema(description = "Provided password", example = "Test777#")
  private String password;

  @Schema(description = "Provided email", example = "Test888@gmail.com")
  private String email;

  public UserLoginRequest() {}

  public UserLoginRequest(String password, String email) {
    this.password = password;
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
