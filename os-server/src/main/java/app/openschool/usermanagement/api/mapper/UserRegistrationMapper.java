package app.openschool.usermanagement.api.mapper;

import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.entities.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/** Useful Javadoc. */
public class UserRegistrationMapper {

  public static User userRegistrationDtoToUser(
      UserRegistrationDto userDto, BCryptPasswordEncoder passwordEncoder) {
    return new User(
        userDto.getFirstName(), userDto.getEmail(), passwordEncoder.encode(userDto.getPassword()));
  }
}
