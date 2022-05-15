package app.openschool.auth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.auth.api.dto.ResetPasswordRequest;
import app.openschool.auth.api.dto.UserRegistrationDto;
import app.openschool.auth.api.exception.EmailAlreadyExistException;
import app.openschool.auth.api.exception.EmailNotFoundException;
import app.openschool.auth.entity.ResetPasswordToken;
import app.openschool.auth.repository.ResetPasswordTokenRepository;
import app.openschool.common.services.CommunicationService;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

  @Mock private UserRepository userRepository;

  @Mock ResetPasswordTokenRepository resetPasswordTokenRepository;

  @Mock private BCryptPasswordEncoder passwordEncoder;

  @Mock private CommunicationService communicationService;

  private AuthService authService;

  @BeforeEach
  void setUp() {
    authService =
        new AuthServiceImpl(
            userRepository, resetPasswordTokenRepository, passwordEncoder, communicationService);
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
        new AuthServiceImpl(
            userRepository, resetPasswordTokenRepository, passwordEncoder, communicationService);

    given(userRepository.findUserByEmail(any())).willReturn(null);

    assertThatThrownBy(() -> authService.loadUserByUsername("testEmail"))
        .isInstanceOf(EmailNotFoundException.class);
  }

  @Test
  void updateResetPasswordToken() {
    User user = new User();
    when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
    authService.updateResetPasswordToken("test@gmail.com", user);
    assertNotNull(userRepository.findByEmail("test@gmail.com"));
  }

  @Test
  void restPasswordToken() {
    User user = new User();
    ResetPasswordToken resetPasswordToken = ResetPasswordToken.generate(user);
    authService.resetPassword(
        new ResetPasswordRequest(resetPasswordToken.getToken(), "test@gmail.com", "test@gmail.com"),
        resetPasswordToken);
    verify(resetPasswordTokenRepository, Mockito.times(1)).delete(resetPasswordToken);
    verify(userRepository, Mockito.times(1)).save(user);
  }
}
