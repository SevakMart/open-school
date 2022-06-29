package app.openschool.auth;

import app.openschool.auth.api.dto.ResetPasswordRequest;
import app.openschool.auth.api.dto.UserLoginDto;
import app.openschool.auth.api.dto.UserRegistrationDto;
import app.openschool.auth.entity.ResetPasswordToken;
import app.openschool.user.User;
import java.util.Optional;

public interface AuthService {

  User register(UserRegistrationDto userDto);

  User findUserByEmail(String email);

  UserLoginDto login(String userEmail);

  User verifyAccount(String token);

  void sendVerificationEmail(Long userId);

  void updateResetPasswordToken(String email, User user);

  void resetPassword(ResetPasswordRequest request, ResetPasswordToken resetPasswordToken);

  Optional<User> findByEmail(String email);

  Optional<ResetPasswordToken> findByToken(String token);

  User validateUserRequestAndReturnUser(Long userId);
}
