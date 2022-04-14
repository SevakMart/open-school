package app.openschool.usermanagement.api.mapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import app.openschool.coursemanagement.entity.Category;
import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.entity.Role;
import app.openschool.usermanagement.entity.User;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserRegistrationMapperTest {

  private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Test
  void userRegistrationDtoToUser() {
    String email = "test@gmail.com";
    String name = "Test";
    String password = "1234$dhjsHH*";
    User user = new User(name, email, password, new Role(1, "STUDENT"));
    UserRegistrationDto userDto = new UserRegistrationDto(name, email, password);
    User mappedUser = UserRegistrationMapper.userRegistrationDtoToUser(userDto, passwordEncoder);
    assertThat(user).isEqualTo(mappedUser);
  }
}
