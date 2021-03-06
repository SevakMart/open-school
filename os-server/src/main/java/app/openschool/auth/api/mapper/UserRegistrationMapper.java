package app.openschool.auth.api.mapper;

import app.openschool.auth.api.dto.UserRegistrationDto;
import app.openschool.auth.api.dto.UserRegistrationResponse;
import app.openschool.user.User;
import app.openschool.user.role.Role;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserRegistrationMapper {

  private static final String ROLE_USER = "STUDENT";

  public static User userRegistrationDtoToUser(
      UserRegistrationDto userDto, BCryptPasswordEncoder passwordEncoder) {
    return new User(
        userDto.getFirstName(),
        userDto.getLastName(),
        userDto.getEmail(),
        passwordEncoder.encode(userDto.getPsd()),
        new Role(1, ROLE_USER));
  }

  public static UserRegistrationResponse toUserRegistrationResponse(User user) {
    return new UserRegistrationResponse(user.getId());
  }
}
