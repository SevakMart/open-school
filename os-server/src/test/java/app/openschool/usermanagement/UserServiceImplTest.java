package app.openschool.usermanagement;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import app.openschool.common.security.JwtTokenProvider;
import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.entities.User;
import app.openschool.usermanagement.exceptions.EmailAlreadyExistException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock private UserRepository userRepository;
  @Mock private BCryptPasswordEncoder passwordEncoder;
  @Mock private JwtTokenProvider jwtTokenProvider;
  private UserService userService;

  @BeforeEach
  void setUp() {
    userService = new UserServiceImpl(userRepository, passwordEncoder, jwtTokenProvider);
  }

  @Test
  void registerUserWithNotUniqEmail() {
    String message = "User with this email already exists";

    given(userRepository.findUserByEmail(any())).willReturn(new User());

    assertThatThrownBy(() -> userService.register(new UserRegistrationDto()))
        .isInstanceOf(EmailAlreadyExistException.class)
        .hasMessageContaining(message);

    verify(userRepository, never()).save(any());
  }

  @Test
  void registerUserWithUniqEmail() {
    given(userRepository.findUserByEmail(any())).willReturn(null);

    userService.register(new UserRegistrationDto());

    verify(userRepository).save(any(User.class));
  }

  @Test
  void findUserByEmail() {
    userService.findUserByEmail(anyString());
    verify(userRepository).findUserByEmail(anyString());
  }
}
