package app.openschool.auth.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import app.openschool.auth.api.dto.UserRegistrationDto;
import app.openschool.auth.api.mapper.UserRegistrationMapper;
import app.openschool.user.User;
import app.openschool.user.role.Role;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserRegistrationMapperTest {

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Test
  void userRegistrationDtoToUser() {
    String email = "test@gmail.com";
    String name = "Test";
    String surname = "Test1";
    String password = "1234$dhjsHH*";
    User user = new User(name, email, password, new Role(1, "STUDENT"));
    UserRegistrationDto userDto = new UserRegistrationDto(name, surname, email, password);
    User mappedUser = UserRegistrationMapper.userRegistrationDtoToUser(userDto, passwordEncoder);
    assertThat(user).isEqualTo(mappedUser);
  }
}
