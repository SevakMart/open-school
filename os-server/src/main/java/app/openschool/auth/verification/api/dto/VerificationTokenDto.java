package app.openschool.auth.verification.api.dto;

public class VerificationTokenDto {

  private String token;

  public VerificationTokenDto(String token) {
    this.token = token;
  }

  public VerificationTokenDto() {}

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}
