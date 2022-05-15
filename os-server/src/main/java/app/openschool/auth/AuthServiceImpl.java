package app.openschool.auth;

import app.openschool.auth.api.dto.ResetPasswordRequest;
import app.openschool.auth.api.dto.UserLoginDto;
import app.openschool.auth.api.dto.UserRegistrationDto;
import app.openschool.auth.api.exception.EmailAlreadyExistException;
import app.openschool.auth.api.exception.EmailNotFoundException;
import app.openschool.auth.api.mapper.UserLoginMapper;
import app.openschool.auth.api.mapper.UserRegistrationMapper;
import app.openschool.auth.entity.ResetPasswordToken;
import app.openschool.auth.repository.ResetPasswordTokenRepository;
import app.openschool.common.security.UserPrincipal;
import app.openschool.common.services.CommunicationService;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.util.Optional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

  private final UserRepository userRepository;
  private final ResetPasswordTokenRepository resetPasswordTokenRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final CommunicationService communicationService;

  public AuthServiceImpl(
      UserRepository userRepository,
      ResetPasswordTokenRepository resetPasswordTokenRepository,
      BCryptPasswordEncoder passwordEncoder,
      CommunicationService communicationService) {
    this.userRepository = userRepository;
    this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    this.passwordEncoder = passwordEncoder;
    this.communicationService = communicationService;
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

  @Override
  public void updateResetPasswordToken(String email, User user) {
    Optional<ResetPasswordToken> currentToken =
        resetPasswordTokenRepository.findByUser(user.getId());
    currentToken.ifPresent(resetPasswordTokenRepository::delete);
    ResetPasswordToken resetPasswordToken = ResetPasswordToken.generate(user);
    resetPasswordTokenRepository.save(resetPasswordToken);
    communicationService.sendResetPasswordEmail(email, resetPasswordToken.getToken());
  }

  @Override
  public void resetPassword(ResetPasswordRequest request, ResetPasswordToken resetPasswordToken) {
    User user = resetPasswordToken.getUser();
    String encodedPassword = passwordEncoder.encode(request.getNewPassword());
    user.setPassword(encodedPassword);
    resetPasswordTokenRepository.delete(resetPasswordToken);
    userRepository.save(user);
  }

  @Override
  public Optional<User> findByEmail(String email) {
    return userRepository.findByEmail(email);
  }

  @Override
  public Optional<ResetPasswordToken> findByToken(String token) {
    return resetPasswordTokenRepository.findByToken(token);
  }
}
