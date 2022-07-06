package app.openschool.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class UserLoginRequest {
  @Schema(description = "Provided password", example = "Test777#")
  private String psd;

  @Schema(description = "Provided email", example = "Test888@gmail.com")
  private String email;

  public UserLoginRequest() {}

  public UserLoginRequest(String psd, String email) {
    this.psd = psd;
    this.email = email;
  }

  public String getPsd() {
    return psd;
  }

  public void setPsd(String psd) {
    this.psd = psd;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
