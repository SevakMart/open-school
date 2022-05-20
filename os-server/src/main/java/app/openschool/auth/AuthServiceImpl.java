package app.openschool.auth;

import app.openschool.auth.api.dto.ResetPasswordRequest;
import app.openschool.auth.api.dto.UserLoginDto;
import app.openschool.auth.api.dto.UserRegistrationDto;
import app.openschool.auth.api.exception.EmailAlreadyExistException;
import app.openschool.auth.api.exception.EmailNotFoundException;
import app.openschool.auth.api.mapper.UserLoginMapper;
import app.openschool.auth.api.mapper.UserRegistrationMapper;
import app.openschool.auth.entity.ResetPasswordToken;
import app.openschool.auth.exception.EmailAlreadyExistException;
import app.openschool.auth.exception.EmailNotExistsException;
import app.openschool.auth.exception.EmailNotFoundException;
import app.openschool.auth.exception.NotMatchingPasswordsException;
import app.openschool.auth.exception.ResetPasswordTokenExpiredException;
import app.openschool.auth.exception.ResetPasswordTokenNotFoundException;
import app.openschool.auth.exception.UserNotVerifiedException;
import app.openschool.auth.mapper.UserLoginMapper;
import app.openschool.auth.mapper.UserRegistrationMapper;
import app.openschool.auth.repository.ResetPasswordTokenRepository;
import app.openschool.auth.verification.VerificationToken;
import app.openschool.auth.verification.VerificationTokenRepository;
import app.openschool.common.event.SendVerificationEmailEvent;
import app.openschool.common.event.SendResetPasswordEmailEvent;
import app.openschool.common.security.UserPrincipal;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.util.Optional;
import org.springframework.context.ApplicationEventPublisher;
import app.openschool.user.api.exception.UserNotFoundException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
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
  private final ApplicationEventPublisher applicationEventPublisher;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final VerificationTokenRepository verificationTokenRepository;
  private final long expiresAt;
  private final Integer tokenExpirationAfterMinutes;

  public AuthServiceImpl(
      UserRepository userRepository,
      ResetPasswordTokenRepository resetPasswordTokenRepository,
      BCryptPasswordEncoder passwordEncoder,
      ApplicationEventPublisher applicationEventPublisher,
      VerificationTokenRepository verificationTokenRepository,
      @Value("${verification.duration}") long expiresAt,
      @Value("${token.expiration}") Integer tokenExpirationAfterMinutes) {
      ApplicationEventPublisher applicationEventPublisher) {
    this.userRepository = userRepository;
    this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    this.passwordEncoder = passwordEncoder;
    this.applicationEventPublisher = applicationEventPublisher;
    this.applicationEventPublisher = applicationEventPublisher;
    this.verificationTokenRepository = verificationTokenRepository;
    this.expiresAt = expiresAt;

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
    applicationEventPublisher.publishEvent(new SendVerificationEmailEvent(this, user));
    return user;
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepository.findUserByEmail(email);
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
      if (!verificationToken.isTokenExpired(fetchedToken.get().getCreatedAt(), expiresAt)) {
        User user = fetchedToken.get().getUser();
        user.setEnabled(true);
        return userRepository.save(user);
      } else {
        applicationEventPublisher.publishEvent(
            new SendVerificationEmailEvent(this, fetchedToken.get().getUser()));
        throw new UserNotVerifiedException();
      }
    }
    throw new UserNotVerifiedException();
  }

  @Override
  public void sendVerificationEmail(Long userId) {
    Optional<User> user = userRepository.findUserById(userId);
    user.orElseThrow(() -> new UserNotFoundException(String.valueOf(userId)));
    applicationEventPublisher.publishEvent(new SendVerificationEmailEvent(this, user.get()));
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
    resetPasswordTokenRepository
        .findByUser(user.getId())
        .ifPresent(resetPasswordTokenRepository::delete);
    ResetPasswordToken resetPasswordToken = ResetPasswordToken.generate(user);
    resetPasswordTokenRepository.save(resetPasswordToken);
    applicationEventPublisher.publishEvent(
        new SendResetPasswordEmailEvent(this, email, resetPasswordToken.getToken()));
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

  private boolean emailAlreadyExist(String email) {
    return findUserByEmail(email) != null;
  }
}
