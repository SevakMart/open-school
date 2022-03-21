package app.openschool.usermanagement;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import app.openschool.usermanagement.entities.Role;
import app.openschool.usermanagement.entities.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
class UserRepositoryTest {

  @Autowired UserRepository userRepository;

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
  }

  @Test
  @Transactional
  void findUserByEmail() {
    String email = "test@gmail.com";
    User user = new User("Test", email, "1234$dhjsHH*", new Role(1, "STUDENT"));
    User registeredUser = userRepository.save(user);
    User fetchedUser = userRepository.findUserByEmail(email);

    assertThat(registeredUser).isEqualTo(fetchedUser);
  }
}
