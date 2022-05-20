package app.openschool.auth.verification;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.role.Role;
import java.time.Instant;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.transaction.annotation.Transactional;

@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
class VerificationTokenRepositoryTest {

  @Autowired VerificationTokenRepository verificationTokenRepository;
  @Autowired UserRepository userRepository;

  @Test
  @Transactional
  void findVerificationTokenByToken() {
    String token = "testToken";
    User user = new User("Test", "testEmail", "1234$dhjsHH*", new Role(1, "STUDENT"));
    userRepository.save(user);
    verificationTokenRepository.save(new VerificationToken(token, Instant.now(), user));

    Optional<VerificationToken> verificationToken =
        verificationTokenRepository.findVerificationTokenByToken(token);
    verificationToken.ifPresent(value -> assertThat(value.getToken()).isEqualTo(token));
  }

  @Test
  void findVerificationTokenByUser() {
    String token = "testToken";
    User user = new User("Test", "testEmail", "1234$dhjsHH*", new Role(1, "STUDENT"));
    userRepository.save(user);
    verificationTokenRepository.save(new VerificationToken(token, Instant.now(), user));

    assertThat(verificationTokenRepository.findVerificationTokenByUser(user).get().getToken())
        .isEqualTo(token);
  }
}
