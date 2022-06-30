package app.openschool.auth.verification.api.dto;

public class VerificationTokenDto {

  private final String token;

  public VerificationTokenDto(String token) {
    this.token = token;
  }

  public String getToken() {
    return token;
  }
}
