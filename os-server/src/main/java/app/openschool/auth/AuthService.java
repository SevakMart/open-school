package app.openschool.auth;

import app.openschool.auth.dto.UserLoginDto;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.auth.verification.VerificationToken;
import app.openschool.user.User;
import java.util.TimeZone;

public interface AuthService {

  User register(UserRegistrationDto userDto, TimeZone timeZone);

  User findUserByEmail(String email);

  UserLoginDto login(String userEmail);

  User verifyAccount(VerificationToken verificationToken, TimeZone timeZone);

  void sendVerificationEmail(Long userId, TimeZone timeZone);
}
