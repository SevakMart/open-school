package app.openschool.auth;

import app.openschool.auth.dto.UserLoginDto;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.user.User;
import app.openschool.user.api.exception.UserNotFoundException;
import org.springframework.http.HttpRequest;

public interface AuthService {

  User register(UserRegistrationDto userDto);

  User findUserByEmail(String email);

  UserLoginDto login(String userEmail);

  void updateResetPasswordToken(HttpRequest httpRequest, String email) throws UserNotFoundException;

//  User getByResetPasswordToken(String token);

  void updatePassword(String token, String newPassword, String confirmedPassword);
}
