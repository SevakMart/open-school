package app.openschool.auth;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.auth.dto.ResetPasswordRequest;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.auth.entity.ResetPasswordToken;
import app.openschool.auth.exception.EmailAlreadyExistException;
import app.openschool.auth.exception.EmailNotExistsException;
import app.openschool.auth.exception.EmailNotFoundException;
import app.openschool.auth.exception.NotMatchingPasswordsException;
import app.openschool.auth.exception.ResetPasswordTokenNotFoundException;
import app.openschool.auth.exception.UserNotVerifiedException;
import app.openschool.auth.repository.ResetPasswordTokenRepository;
import app.openschool.auth.verification.VerificationToken;
import app.openschool.auth.verification.VerificationTokenRepository;
import app.openschool.common.services.CommunicationService;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.api.exception.UserNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

  @Mock private UserRepository userRepository;

  @Mock ResetPasswordTokenRepository resetPasswordTokenRepository;

  @Mock private VerificationTokenRepository verificationTokenRepository;

  @Mock CommunicationService communicationService;

  @Mock private BCryptPasswordEncoder passwordEncoder;

  @Value("${token.expiration}")
  private int tokenExpirationAfterMinutes;

  private AuthService authService;

  @BeforeEach
  void setUp() {
    authService =
        new AuthServiceImpl(
            userRepository,
            resetPasswordTokenRepository,
            passwordEncoder,
            communicationService,
            verificationTokenRepository,
            0L,
            tokenExpirationAfterMinutes);
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
            userRepository,
            resetPasswordTokenRepository,
            passwordEncoder,
            null,
            null,
            0L,
            tokenExpirationAfterMinutes);

    given(userRepository.findUserByEmail(any())).willReturn(null);

    assertThatThrownBy(() -> authService.loadUserByUsername("testEmail"))
        .isInstanceOf(EmailNotFoundException.class);
  }

  @Test
  void verifyAccountWithWrongToken() {
    VerificationToken verificationToken = new VerificationToken();
    verificationToken.setToken("testToken");
    given(verificationTokenRepository.findVerificationTokenByToken(any()))
        .willReturn(Optional.empty());

    assertThatThrownBy(() -> authService.verifyAccount(verificationToken))
        .isInstanceOf(UserNotVerifiedException.class);
  }

  @Test
  void sendVerificationEmailWithWrongUserId() {
    long userId = 1L;
    given(userRepository.findUserById(userId)).willReturn(null);

    assertThatThrownBy(() -> authService.sendVerificationEmail(userId))
        .isInstanceOf(UserNotFoundException.class);
  }

  @Test
  void updateResetPasswordTokenUserWithGivenEmailNotFound() {
    when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.empty());
    assertThatThrownBy(() -> authService.updateResetPasswordToken("test@gmail.com"))
        .isInstanceOf(EmailNotExistsException.class);
    verify(userRepository, Mockito.times(1)).findByEmail("test@gmail.com");
  }

  @Test
  void updateResetPasswordToken() {
    User user = new User();
    when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
    authService.updateResetPasswordToken("test@gmail.com");
    verify(userRepository, Mockito.times(1)).findByEmail("test@gmail.com");
    assertNotNull(userRepository.findByEmail("test@gmail.com"));
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
    verify(resetPasswordTokenRepository, Mockito.times(1)).findByToken("5597");
  }

  @Test
  void restPasswordToken() {
    User user = new User();
    ResetPasswordToken resetPasswordToken = ResetPasswordToken.generate(user);
    when(resetPasswordTokenRepository.findByToken(resetPasswordToken.getToken()))
        .thenReturn(Optional.of(resetPasswordToken));
    authService.resetPassword(
        new ResetPasswordRequest(
            resetPasswordToken.getToken(), "test@gmail.com", "test@gmail.com"));
    verify(resetPasswordTokenRepository, Mockito.times(1)).delete(resetPasswordToken);
    verify(userRepository, Mockito.times(1)).save(user);
  }
}
