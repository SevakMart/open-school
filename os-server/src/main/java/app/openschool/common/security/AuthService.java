package app.openschool.common.security;

import app.openschool.common.security.api.dto.UserLoginDto;
import app.openschool.common.security.api.dto.UserRegistrationDto;
import app.openschool.user.User;

public interface AuthService {

  User register(UserRegistrationDto userDto);

  User findUserByEmail(String email);

  UserLoginDto login(String userEmail);
}
