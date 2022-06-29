package app.openschool.auth.verification.api.mapper;

import app.openschool.auth.verification.VerificationToken;
import app.openschool.auth.verification.api.dto.VerificationTokenDto;

public class VerificationTokenMapper {

  private VerificationTokenMapper() {}

  public static VerificationTokenDto verificationTokenToVerificationTokenDto(
      VerificationToken verificationToken) {
    return new VerificationTokenDto(verificationToken.getToken());
  }
}
