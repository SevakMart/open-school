package app.openschool.auth;

import app.openschool.auth.dto.ResetPasswordRequest;
import app.openschool.auth.dto.UserLoginDto;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.auth.verification.VerificationToken;
import app.openschool.user.User;

public interface AuthService {

  User register(UserRegistrationDto userDto);

  User findUserByEmail(String email);

  UserLoginDto login(String userEmail);

  User verifyAccount(VerificationToken verificationToken);

  void sendVerificationEmail(Long userId);

  void updateResetPasswordToken(String email);

  void resetPassword(ResetPasswordRequest request);
}
