package app.openschool.auth;

import app.openschool.auth.api.dto.ResetPasswordRequest;
import app.openschool.auth.api.dto.UserLoginDto;
import app.openschool.auth.api.dto.UserRegistrationDto;
import app.openschool.auth.api.exception.EmailNotFoundException;
import app.openschool.auth.api.exception.TokenValidationException;
import app.openschool.auth.api.exception.UserVerificationException;
import app.openschool.auth.api.mapper.UserLoginMapper;
import app.openschool.auth.api.mapper.UserRegistrationMapper;
import app.openschool.auth.entity.ResetPasswordToken;
import app.openschool.auth.repository.ResetPasswordTokenRepository;
import app.openschool.auth.verification.VerificationToken;
import app.openschool.auth.verification.VerificationTokenRepository;
import app.openschool.common.event.SendResetPasswordEmailEvent;
import app.openschool.common.event.SendVerificationEmailEvent;
import app.openschool.common.security.UserPrincipal;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

  private final UserRepository userRepository;
  private final ResetPasswordTokenRepository resetPasswordTokenRepository;
  private final VerificationTokenRepository verificationTokenRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final long expiresAt;

  public AuthServiceImpl(
      UserRepository userRepository,
      ResetPasswordTokenRepository resetPasswordTokenRepository,
      VerificationTokenRepository verificationTokenRepository,
      BCryptPasswordEncoder passwordEncoder,
      ApplicationEventPublisher applicationEventPublisher,
      @Value("${verification.duration}") long expiresAt) {
    this.userRepository = userRepository;
    this.resetPasswordTokenRepository = resetPasswordTokenRepository;
    this.verificationTokenRepository = verificationTokenRepository;
    this.passwordEncoder = passwordEncoder;
    this.applicationEventPublisher = applicationEventPublisher;
    this.expiresAt = expiresAt;
  }

  @Override
  public User register(UserRegistrationDto userDto) {
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
  public void sendVerificationEmail(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(IllegalArgumentException::new);
    checkingUserCondition(user);
    applicationEventPublisher.publishEvent(new SendVerificationEmailEvent(this, user));
  }

  @Override
  public Optional<User> verifyAccount(String token) {
    VerificationToken fetchedToken =
        verificationTokenRepository
            .findVerificationTokenByToken(token)
            .orElseThrow(TokenValidationException::new);

    checkingConditionsBeforeVerification(fetchedToken);
    User user = fetchedToken.getUser();

    user.setEnabled(true);
    return Optional.of(userRepository.save(user));
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

  @Override
  public User validateUserRequestAndReturnUser(Long userId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = findUserByEmail(username);
    if (!user.getId().equals(userId)) {
      throw new IllegalArgumentException();
    }
    return user;
  }

  @Override
  public boolean emailAlreadyExist(String email) {
    return findByEmail(email).isPresent();
  }

  private void checkingConditionsBeforeVerification(VerificationToken token) {
    checkingUserCondition(token.getUser());
    checkingTokenCondition(token);
  }

  private void checkingTokenCondition(VerificationToken verifiableToken) {
    if (VerificationToken.isTokenExpired(verifiableToken.getCreatedAt(), expiresAt)) {
      throw new TokenValidationException();
    }
  }

  private void checkingUserCondition(User verifiableUser) {
    if (Boolean.TRUE.equals(verifiableUser.isEnabled())) {
      throw new UserVerificationException();
    }
  }
}
