package app.openschool.auth;

import app.openschool.auth.dto.ResetPasswordRequest;
import app.openschool.auth.dto.UserLoginDto;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.user.User;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;

public interface AuthService {

  User register(UserRegistrationDto userDto);

  User findUserByEmail(String email);

  UserLoginDto login(String userEmail);

  void updateResetPasswordToken(String email)
      throws MessagingException, UnsupportedEncodingException;

  void resetPassword(ResetPasswordRequest request);
}
