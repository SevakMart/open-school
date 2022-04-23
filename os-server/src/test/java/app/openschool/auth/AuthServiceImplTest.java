package app.openschool.auth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.auth.exception.EmailAlreadyExistException;
import app.openschool.auth.exception.EmailNotFoundException;
import app.openschool.auth.verification.VerificationTokenRepository;
import app.openschool.common.services.EmailSenderService;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.ITemplateEngine;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

  @Mock private UserRepository userRepository;

  @Mock private BCryptPasswordEncoder passwordEncoder;

  @Mock private ITemplateEngine templateEngine;

  @Mock private VerificationTokenRepository verificationTokenRepository;

  @Mock private EmailSenderService emailSenderService;

  private AuthService authService;

  @BeforeEach
  void setUp() {
    authService =
        new AuthServiceImpl(
            userRepository,
            passwordEncoder,
            emailSenderService,
            templateEngine,
            verificationTokenRepository);
  }

  @Test
  void registerUserWithNotUniqEmail() {

    given(userRepository.findUserByEmail(any())).willReturn(new User());

    assertThatThrownBy(() -> authService.register(new UserRegistrationDto()))
        .isInstanceOf(EmailAlreadyExistException.class);

    verify(userRepository, never()).save(any());
  }

  @Test
  void registerUserWithUniqEmail() {
    given(userRepository.findUserByEmail(any())).willReturn(null);
    doReturn(new User("John", "testPass")).when(userRepository).save(any());
    doReturn("done").when(templateEngine).process(anyString(), any());
    doNothing().when(emailSenderService).sendEmail(anyString(), anyString());

    authService.register(new UserRegistrationDto());

    verify(userRepository).save(any(User.class));
  }

  @Test
  void findUserByEmail() {
    authService.findUserByEmail(anyString());
    verify(userRepository).findUserByEmail(anyString());
  }

  @Test
  void loadNonexistentUserByUsername() {
    AuthServiceImpl authService =
        new AuthServiceImpl(userRepository, passwordEncoder, null, null, null);

    given(userRepository.findUserByEmail(any())).willReturn(null);

    assertThatThrownBy(() -> authService.loadUserByUsername("testEmail"))
        .isInstanceOf(EmailNotFoundException.class);
  }
}
