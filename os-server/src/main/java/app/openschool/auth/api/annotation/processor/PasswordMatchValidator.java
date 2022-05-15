package app.openschool.auth.api.annotation.processor;

import app.openschool.auth.api.annotation.PasswordsMatch;
import app.openschool.auth.api.dto.ResetPasswordRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator
    implements ConstraintValidator<PasswordsMatch, ResetPasswordRequest> {

  @Override
  public void initialize(PasswordsMatch passwordsMatch) {}

  @Override
  public boolean isValid(ResetPasswordRequest request, ConstraintValidatorContext ctx) {
    if (!request.getNewPassword().equals(request.getConfirmedPassword())) {
      ctx.disableDefaultConstraintViolation();
      ctx.buildConstraintViolationWithTemplate("{passwords.mismatch}")
          .addPropertyNode("confirmedPassword")
          .addConstraintViolation();
      return false;
    }
    return true;
  }
}
