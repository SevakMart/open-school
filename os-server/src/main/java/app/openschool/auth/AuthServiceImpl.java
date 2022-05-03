package app.openschool.auth;

import app.openschool.auth.dto.ResetPasswordRequest;
import app.openschool.auth.dto.UserLoginDto;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.auth.entity.ResetPasswordToken;
import app.openschool.auth.exception.EmailAlreadyExistException;
import app.openschool.auth.exception.EmailNotExistsException;
import app.openschool.auth.exception.EmailNotFoundException;
import app.openschool.auth.exception.UserNotVerifiedException;
import app.openschool.auth.exception.NotMatchingPasswordsException;
import app.openschool.auth.exception.ResetPasswordTokenExpiredException;
import app.openschool.auth.exception.ResetPasswordTokenNotFoundException;
import app.openschool.auth.mapper.UserLoginMapper;
import app.openschool.auth.mapper.UserRegistrationMapper;
import app.openschool.auth.verification.VerificationToken;
import app.openschool.auth.verification.VerificationTokenRepository;
import app.openschool.auth.repository.ResetPasswordTokenRepository;
import app.openschool.common.security.UserPrincipal;
import app.openschool.common.services.CommunicationService;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.api.exception.UserNotFoundException;
import java.time.Duration;
import java.time.Instant;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
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
  private final VerificationTokenRepository verificationTokenRepository;
  private final long expiresAt;
  private final CommunicationService communicationService;
  private final Integer tokenExpirationAfterMinutes;

  public AuthServiceImpl(
      UserRepository userRepository,
      ResetPasswordTokenRepository resetPasswordTokenRepository,
      BCryptPasswordEncoder passwordEncoder,
      CommunicationService communicationService,
      @Value("${token.expiration}") Integer tokenExpirationAfterMinutes) {
  public AuthServiceImpl(
      UserRepository userRepository,
      BCryptPasswordEncoder passwordEncoder,
      CommunicationService communicationService,
      VerificationTokenRepository verificationTokenRepository,
      @Value("${verification.duration}") long expiresAt) {
    this.userRepository = userRepository;
    this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    this.passwordEncoder = passwordEncoder;
    this.communicationService = communicationService;
    this.verificationTokenRepository = verificationTokenRepository;
    this.expiresAt = expiresAt;
    this.communicationService = communicationService;
    this.tokenExpirationAfterMinutes = tokenExpirationAfterMinutes;
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
    Optional<VerificationToken> fetchedToken =
        verificationTokenRepository.findVerificationTokenByToken(verificationToken.getToken());

    if (fetchedToken.isPresent()) {
      if (!isTokenExpired(fetchedToken.get().getCreatedAt())) {
        User user = fetchedToken.get().getUser();
        user.setEnabled(true);
        return userRepository.save(user);
      } else {
        communicationService.sendEmailToVerifyUserAccount(fetchedToken.get().getUser());
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

  @Override
  public void updateResetPasswordToken(String email) {
    User user =
        userRepository.findByEmail(email).orElseThrow(() -> new EmailNotExistsException(email));
    if (resetPasswordTokenRepository.findByUser(user.getId()).isPresent()) {
      ResetPasswordToken currentToken = resetPasswordTokenRepository.findByUser(user.getId()).get();
      resetPasswordTokenRepository.delete(currentToken);
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

  private boolean isTokenExpired(Instant createdAt) {
    Duration difference = Duration.between(createdAt, Instant.now());
    return difference.toMinutes() > expiresAt;
  }
}
