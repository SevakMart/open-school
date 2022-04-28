package app.openschool.auth;

import app.openschool.auth.dto.UserLoginDto;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.auth.exception.EmailAlreadyExistException;
import app.openschool.auth.exception.EmailNotFoundException;
import app.openschool.auth.exception.UserNotVerifiedException;
import app.openschool.auth.mapper.UserLoginMapper;
import app.openschool.auth.mapper.UserRegistrationMapper;
import app.openschool.auth.verification.VerificationToken;
import app.openschool.auth.verification.VerificationTokenRepository;
import app.openschool.common.security.UserPrincipal;
import app.openschool.common.services.CommunicationService;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.api.exception.UserNotFoundException;
import java.time.Duration;
import java.time.Instant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final CommunicationService communicationService;
  private final VerificationTokenRepository verificationTokenRepository;
  private final long expiresAt;

  public AuthServiceImpl(
      UserRepository userRepository,
      BCryptPasswordEncoder passwordEncoder,
      CommunicationService communicationService,
      VerificationTokenRepository verificationTokenRepository,
      @Value("${verification.duration}") long expiresAt) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.communicationService = communicationService;
    this.verificationTokenRepository = verificationTokenRepository;
    this.expiresAt = expiresAt;
  }

  @Override
  public User register(UserRegistrationDto userDto) {

    if (emailAlreadyExist(userDto.getEmail())) {
      throw new EmailAlreadyExistException();
    }

    User user =
        userRepository.save(
            UserRegistrationMapper.userRegistrationDtoToUser(userDto, passwordEncoder));
    communicationService.sendEmailToVerifyUserAccount(user);
    return user;
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
  public User verifyAccount(VerificationToken verificationToken) {
    VerificationToken fetchedToken =
        verificationTokenRepository.findVerificationTokenByToken(verificationToken.getToken());
    if (fetchedToken != null) {
      if (!isTokenExpired(fetchedToken.getCreatedAt())) {
        User user = fetchedToken.getUser();
        user.setEnabled(true);
        return userRepository.save(user);
      } else {
        communicationService.sendEmailToVerifyUserAccount(fetchedToken.getUser());
        throw new UserNotVerifiedException();
      }
    }
    throw new UserNotVerifiedException();
  }

  @Override
  public void sendVerificationEmail(Long userId) {
    User user = userRepository.findUserById(userId);
    if (user == null) {
      throw new UserNotFoundException(String.valueOf(userId));
    }
    communicationService.sendEmailToVerifyUserAccount(user);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findUserByEmail(email);
    if (user == null) {
      throw new EmailNotFoundException(email);
    }
    return new UserPrincipal(user);
  }

  private boolean isTokenExpired(Instant createdAt) {
    Duration difference = Duration.between(Instant.now(), createdAt);
    return difference.toMinutes() < expiresAt;
  }
}
