package app.openschool.auth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.auth.dto.ResetPasswordRequest;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.auth.exception.EmailAlreadyExistException;
import app.openschool.auth.exception.EmailNotExistsException;
import app.openschool.auth.exception.EmailNotFoundException;
import app.openschool.auth.exception.NotMatchingPasswordsException;
import app.openschool.auth.exception.ResetPasswordTokenNotFoundException;
import app.openschool.common.communication.Communication;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
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

  @Mock private BCryptPasswordEncoder passwordEncoder;

  @Mock private Communication communication;

  private AuthService authService;

  @BeforeEach
  void setUp() {
    authService = new AuthServiceImpl(userRepository, passwordEncoder, communication);
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
        new AuthServiceImpl(userRepository, passwordEncoder, communication);

    given(userRepository.findUserByEmail(any())).willReturn(null);

    assertThatThrownBy(() -> authService.loadUserByUsername("testEmail"))
        .isInstanceOf(EmailNotFoundException.class);
  }

  @Test
  void updateResetPasswordTokenUserWithGivenEmailNotFound() {
    when(userRepository.findUserByEmail("test@gmail.com")).thenReturn(null);
    assertThatThrownBy(() -> authService.updateResetPasswordToken("test@gmail.com"))
        .isInstanceOf(EmailNotExistsException.class);
    verify(userRepository, Mockito.times(1)).findUserByEmail("test@gmail.com");
  }

  @Test
  void updateResetPasswordToken() throws MessagingException, UnsupportedEncodingException {
    User user = new User();
    when(userRepository.findUserByEmail("test@gmail.com")).thenReturn(user);
    authService.updateResetPasswordToken("test@gmail.com");
    verify(userRepository, Mockito.times(1)).findUserByEmail("test@gmail.com");
    assertNotNull(userRepository.findUserByEmail("test@gmail.com"));
    assertNotNull(user.getResetPasswordToken());
    verify(userRepository, Mockito.times(1)).save(user);
  }

  @Test
  void restPasswordTokenFails() {

    assertThatThrownBy(
            () ->
                authService.resetPassword(
                    new ResetPasswordRequest("5597", "test@gmail.com", "t@gmail.com")))
        .isInstanceOf(NotMatchingPasswordsException.class);
    assertThatThrownBy(
            () ->
                authService.resetPassword(
                    new ResetPasswordRequest("5597", "test@gmail.com", "test@gmail.com")))
        .isInstanceOf(ResetPasswordTokenNotFoundException.class);
    verify(userRepository, Mockito.times(1)).findByResetPasswordToken("5597");
  }

  @Test
  void restPasswordToken() {
    User user = new User();
    user.setResetPasswordToken("7483");
    when(userRepository.findByResetPasswordToken("7483")).thenReturn(user);
    authService.resetPassword(new ResetPasswordRequest("7483", "test@gmail.com", "test@gmail.com"));
    verify(userRepository, Mockito.times(1)).findByResetPasswordToken("7483");
    assertNotNull(userRepository.findByResetPasswordToken("7483"));
    verify(userRepository, Mockito.times(1)).save(user);
  }
}
