package app.openschool.usermanagement;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import app.openschool.coursemanagement.entities.Category;
import app.openschool.usermanagement.entities.Role;
import app.openschool.usermanagement.entities.User;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class UserRepositoryTest {

  @Autowired UserRepository userRepository;

  @Test
  @Transactional
  void findUserByEmail() {
    String email = "test@gmail.com";
    Set<Category> categories = new HashSet<>();
    Category category = new Category("Java", 2);
    categories.add(category);
    User user = new User("Test", email, "1234$dhjsHH*", categories, new Role(1, "STUDENT"));
    User registeredUser = userRepository.save(user);
    User fetchedUser = userRepository.findUserByEmail(email);

    assertThat(registeredUser).isEqualTo(fetchedUser);
    assertTrue(fetchedUser.getCategories().contains(category));
    assertEquals(1, fetchedUser.getCategories().size());
  }
}
