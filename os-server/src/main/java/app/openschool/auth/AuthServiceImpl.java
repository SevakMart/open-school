package app.openschool.auth;

import app.openschool.auth.dto.ResetPasswordRequest;
import app.openschool.auth.dto.UserLoginDto;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.auth.entity.ResetPasswordToken;
import app.openschool.auth.exception.EmailAlreadyExistException;
import app.openschool.auth.exception.EmailNotExistsException;
import app.openschool.auth.exception.EmailNotFoundException;
import app.openschool.auth.exception.NotMatchingPasswordsException;
import app.openschool.auth.exception.ResetPasswordTokenExpiredException;
import app.openschool.auth.exception.ResetPasswordTokenNotFoundException;
import app.openschool.auth.mapper.UserLoginMapper;
import app.openschool.auth.mapper.UserRegistrationMapper;
import app.openschool.auth.repository.ResetPasswordTokenRepository;
import app.openschool.common.security.UserPrincipal;
import app.openschool.common.services.CommunicationService;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import org.springframework.beans.factory.annotation.Value;
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
  private final Integer tokenExpirationAfterMinutes;

  public AuthServiceImpl(
      UserRepository userRepository,
      ResetPasswordTokenRepository resetPasswordTokenRepository,
      BCryptPasswordEncoder passwordEncoder,
      CommunicationService communicationService,
      @Value("${token.expiration}") Integer tokenExpirationAfterMinutes) {
    this.userRepository = userRepository;
    this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    this.passwordEncoder = passwordEncoder;
    this.communicationService = communicationService;
    this.tokenExpirationAfterMinutes = tokenExpirationAfterMinutes;
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
  public void updateResetPasswordToken(String email) {
    User user =
        userRepository.findByEmail(email).orElseThrow(() -> new EmailNotExistsException(email));
    if (resetPasswordTokenRepository.findByUser(user.getId()).isPresent()) {
      resetPasswordTokenRepository.findByUser(user.getId()).ifPresent(resetPasswordTokenRepository::delete);
    }
    ResetPasswordToken resetPasswordToken = ResetPasswordToken.generate(user);
    resetPasswordTokenRepository.save(resetPasswordToken);
    communicationService.sendResetPasswordEmail(email, resetPasswordToken.getToken());
  }

  @Override
  public void resetPassword(ResetPasswordRequest request) {
    if (!request.getNewPassword().equals(request.getConfirmedPassword())) {
      throw new NotMatchingPasswordsException();
    }
    ResetPasswordToken resetPasswordToken =
        resetPasswordTokenRepository
            .findByToken(request.getToken())
            .orElseThrow(ResetPasswordTokenNotFoundException::new);
    if (resetPasswordToken.isExpired(tokenExpirationAfterMinutes)) {
      throw new ResetPasswordTokenExpiredException();
    }
    User user = resetPasswordToken.getUser();
    if (user == null) {
      throw new ResetPasswordTokenNotFoundException();
    }
    String encodedPassword = passwordEncoder.encode(request.getNewPassword());
    user.setPassword(encodedPassword);
    resetPasswordTokenRepository.delete(resetPasswordToken);
    userRepository.save(user);
  }
}
