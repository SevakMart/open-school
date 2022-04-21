package app.openschool.auth;

import app.openschool.auth.dto.UserLoginDto;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.auth.exception.EmailAlreadyExistException;
import app.openschool.auth.exception.EmailNotFoundException;
import app.openschool.auth.mapper.UserLoginMapper;
import app.openschool.auth.mapper.UserRegistrationMapper;
import app.openschool.common.security.UserPrincipal;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.api.exception.UserNotFoundException;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  public AuthServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User register(UserRegistrationDto userDto) {

    if (emailAlreadyExist(userDto.getEmail())) {
      throw new EmailAlreadyExistException();
    }

    User user = UserRegistrationMapper.userRegistrationDtoToUser(userDto, passwordEncoder);
    return userRepository.save(user);
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepository.findUserByEmail(email);
  }

  private boolean emailAlreadyExist(String email) {
    return findUserByEmail(email) != null;
  }

  @Override
  public UserLoginDto login(String userEmail) {
    return UserLoginMapper.toUserLoginDto(userRepository.findUserByEmail(userEmail));
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findUserByEmail(email);
    if (user == null) {
      throw new EmailNotFoundException(email);
    }
    return new UserPrincipal(user);
  }

  public void updateResetPasswordToken(HttpRequest httpRequest, String email) throws UserNotFoundException {
    String token = RandomString.make(45);
    String resetPasswordLink = httpRequest.getURI().getHost() + "/reset_password?token=" + token;
    sendEmail(email, resetPasswordLink);

    User user = userRepository.findUserByEmail(email);
    if (user != null) {
      user.setResetPasswordToken(token);
      userRepository.save(user);
    } else {
      throw new UserNotFoundException("");
    }
  }

  public User getByResetPasswordToken(String token) {
    return userRepository.findByResetPasswordToken(token);
  }

  public void updatePassword(String token, String password, String confirmedPassword) {

    User user = userRepository.findByResetPasswordToken(token);



    String encodedPassword = passwordEncoder.encode(newPassword);
    user.setPassword(encodedPassword);
    user.setResetPasswordToken(null);
    userRepository.save(user);
  }
}
