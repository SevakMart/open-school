package app.openschool.usermanagement;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import app.openschool.usermanagement.entities.Role;
import app.openschool.usermanagement.entities.User;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

  @Test
  void findAllMentorsCheckIsMentor() {
    List<User> mentorsList = userRepository.findAllMentors(PageRequest.of(0, 5)).toList();
    for (User mentor : mentorsList) {
      assertEquals("MENTOR", mentor.getRole().getType());
    }
  }

  @Test
  void findAllMentorsCheckPageable() {
    Page<User> allMentorsPage = userRepository.findAllMentors(PageRequest.of(0, 3));
    double pageCount = allMentorsPage.getTotalPages();
    double mentorsCount = allMentorsPage.getTotalElements();
    assertThat(pageCount).isEqualTo(Math.ceil(mentorsCount / 3));
  }
}
