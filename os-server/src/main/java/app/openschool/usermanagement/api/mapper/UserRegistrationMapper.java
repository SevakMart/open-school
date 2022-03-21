package app.openschool.usermanagement.api.mapper;

import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.entities.Role;
import app.openschool.usermanagement.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserRegistrationMapper {

  private static final String ROLE_USER = "STUDENT";

  public static User userRegistrationDtoToUser(
      UserRegistrationDto userDto, BCryptPasswordEncoder passwordEncoder) {
    return new User(
        userDto.getFirstName(),
        userDto.getEmail(),
        passwordEncoder.encode(userDto.getPassword()),
        new Role(1, ROLE_USER));
  }
}
