package app.openschool.usermanagement.service;

import app.openschool.common.security.JwtTokenProvider;
import app.openschool.usermanagement.api.dto.MentorDto;
import app.openschool.usermanagement.api.dto.UserLoginResponse;
import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.api.exceptions.EmailAlreadyExistException;
import app.openschool.usermanagement.api.mapper.MentorMapper;
import app.openschool.usermanagement.api.mapper.UserLoginMapper;
import app.openschool.usermanagement.api.mapper.UserRegistrationMapper;
import app.openschool.usermanagement.entity.User;
import app.openschool.usermanagement.entity.UserPrincipal;
import app.openschool.usermanagement.repository.UserRepository;
import org.apache.logging.log4j.message.StringFormattedMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

  private static final String EMAIL_ALREADY_EXISTS = "User with this email already exists";
  private static final String TOKEN_PREFIX = "Bearer ";

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;

  public UserServiceImpl(
      UserRepository userRepository,
      BCryptPasswordEncoder passwordEncoder,
      JwtTokenProvider jwtTokenProvider) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  public User register(UserRegistrationDto userDto) {

    if (emailAlreadyExist(userDto.getEmail())) {
      throw new EmailAlreadyExistException(EMAIL_ALREADY_EXISTS);
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
  public Page<MentorDto> findAllMentors(Pageable pageable) {
    return MentorMapper.toMentorDtoPage(userRepository.findAllMentors(pageable));
  }

  @Override
  public UserLoginResponse login(String userEmail) {
    User user = userRepository.findUserByEmail(userEmail);
    UserLoginResponse userLoginResponse = new UserLoginResponse();
    userLoginResponse.setToken(
        TOKEN_PREFIX + jwtTokenProvider.generateJwtToken(new UserPrincipal(user)));
    userLoginResponse.setUserLoginDto(UserLoginMapper.toUserLoginDto(user));
    return userLoginResponse;
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findUserByEmail(email);
    if (user == null) {
      String message =
          String.valueOf(new StringFormattedMessage("User with this %s email not exist", email));
      throw new BadCredentialsException(message);
    }
    return new UserPrincipal(user);
  }
}
