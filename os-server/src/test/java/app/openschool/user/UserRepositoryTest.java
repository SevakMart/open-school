package app.openschool.user;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import app.openschool.category.Category;
import app.openschool.user.role.Role;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    Category category = new Category("Java", null);
    categories.add(category);
    User user = new User("Test", email, "1234$dhjsHH*", categories, new Role(1, "STUDENT"));
    User registeredUser = userRepository.save(user);
    User fetchedUser = userRepository.findUserByEmail(email);

    assertThat(registeredUser).isEqualTo(fetchedUser);
    assertTrue(fetchedUser.getCategories().contains(category));
    assertEquals(1, fetchedUser.getCategories().size());
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

  @Test
  @Transactional
  void findUserById() {
    User user = new User("Test", "testEmail", "testPass", new Role(1, "STUDENT"));
    userRepository.save(user);
    Optional<User> fetchedUser = userRepository.findById(1L);

    assertThat(user).isEqualTo(fetchedUser.get());
  }

  @Test
  void findMentorsByName() {
    String roleMentor = "MENTOR";
    String roleStudent = "STUDENT";
    userRepository.save(new User("John", "Doe", "testEmail", "testPass", new Role(3, roleMentor)));
    userRepository.save(new User("Doe", "John", "testEmail2", "testPass", new Role(3, roleMentor)));
    userRepository.save(
        new User("Doe", "John", "testEmail3", "testPass", new Role(1, roleStudent)));

    Optional<Page<User>> jo = userRepository.findMentorsByName("jo", PageRequest.of(0, 5));
    List<User> mentorsList = jo.get().toList();

    assertEquals(2, mentorsList.size());
    mentorsList.forEach(mentor -> assertEquals(roleMentor, mentor.getRole().getType()));
  }

  @Test
  void findSavedMentorsByName() {
    String roleMentor = "MENTOR";
    String roleStudent = "STUDENT";
    User mentor1 = new User("John", "Doe", "testEmail", "testPass", new Role(3, roleMentor));
    User mentor2 = new User("Doe", "John", "testEmail2", "testPass", new Role(3, roleMentor));
    User student = new User("Doe", "John", "testEmail3", "testPass", new Role(1, roleStudent));
    student.setMentors(Set.of(mentor1, mentor2));
    userRepository.save(mentor1);
    userRepository.save(mentor2);
    userRepository.save(student);

    List<User> mentorsList =
        userRepository.findSavedMentorsByName(3L, "jo", PageRequest.of(0, 5)).toList();

    assertEquals(2, mentorsList.size());
    mentorsList.forEach(mentor -> assertEquals(roleMentor, mentor.getRole().getType()));
  }
}
