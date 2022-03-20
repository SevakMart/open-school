package app.openschool.usermanagement;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import app.openschool.usermanagement.entities.Role;
import app.openschool.usermanagement.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class UserRepositoryTest {

  @Autowired UserRepository userRepository;

  @Test
  @Transactional
  void findUserByEmail() {
    String email = "ani@gmail.com";
    User user = new User("Ani", email, "1234$dhjsHH*", new Role(1, "Student"));
    User registeredUser = userRepository.save(user);
    User fetchedUser = userRepository.findUserByEmail(email);

    assertThat(registeredUser).isEqualTo(fetchedUser);
  }
}
