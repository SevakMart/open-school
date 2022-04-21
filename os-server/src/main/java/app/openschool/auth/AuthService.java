package app.openschool.auth;

import app.openschool.auth.dto.UserLoginDto;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.user.User;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import org.springframework.http.HttpRequest;

public interface AuthService {

  User register(UserRegistrationDto userDto);

  User findUserByEmail(String email);

  UserLoginDto login(String userEmail);

  void sendEmail(String recipientEmail, String link)
      throws MessagingException, UnsupportedEncodingException;

  void updateResetPasswordToken(String email)
      throws MessagingException, UnsupportedEncodingException;

  void updatePassword(String token, String newPassword, String confirmedPassword);
}
