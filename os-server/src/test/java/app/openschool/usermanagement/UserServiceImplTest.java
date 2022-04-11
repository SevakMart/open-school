package app.openschool.usermanagement;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.common.security.JwtTokenProvider;
import app.openschool.usermanagement.api.UserGenerator;
import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.api.exceptions.EmailAlreadyExistException;
import app.openschool.usermanagement.entity.User;
import app.openschool.usermanagement.repository.UserRepository;
import app.openschool.usermanagement.service.UserService;
import app.openschool.usermanagement.service.UserServiceImpl;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

  @Test
  void findAllMentors() {
    List<User> userList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      userList.add(UserGenerator.generateUser());
    }
    Pageable pageable = PageRequest.of(0, 2);
    Page<User> userPage = new PageImpl<>(userList, pageable, 5);
    when(userRepository.findAllMentors(pageable)).thenReturn(userPage);
    Assertions.assertEquals(3, userService.findAllMentors(pageable).getTotalPages());
    Assertions.assertEquals(5, userService.findAllMentors(pageable).getTotalElements());
    verify(userRepository, Mockito.times(2)).findAllMentors(pageable);
  }
}
