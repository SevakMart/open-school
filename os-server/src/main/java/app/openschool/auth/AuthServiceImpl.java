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
import app.openschool.common.services.EmailSenderService;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;
  private final EmailSenderService emailSender;
  private final ITemplateEngine templateEngine;
  private final VerificationTokenRepository verificationTokenRepository;

  public AuthServiceImpl(
      UserRepository userRepository,
      BCryptPasswordEncoder passwordEncoder,
      EmailSenderService emailSender,
      ITemplateEngine templateEngine,
      VerificationTokenRepository verificationTokenRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.emailSender = emailSender;
    this.templateEngine = templateEngine;
    this.verificationTokenRepository = verificationTokenRepository;
  }

  @Override
  public User register(UserRegistrationDto userDto) {

    if (emailAlreadyExist(userDto.getEmail())) {
      throw new EmailAlreadyExistException();
    }

    User user =
        userRepository.save(
            UserRegistrationMapper.userRegistrationDtoToUser(userDto, passwordEncoder));
    sendEmailToVerifyUserAccount(user);
    return user;
  }

  private void sendEmailToVerifyUserAccount(User user) {
    String token = UUID.randomUUID().toString() + user.getId();
    long expiresAt = ZonedDateTime.now().plusDays(1).toInstant().toEpochMilli();
    String date = new SimpleDateFormat("dd MMM yyyy HH:mm:ss").format(new Date(expiresAt));
    VerificationToken verificationToken = new VerificationToken(user, token, expiresAt);
    verificationTokenRepository.save(verificationToken);
    emailSender.sendEmail(
        user.getEmail(), createEmailContent(user.getName(), date, verificationToken));
  }

  private String createEmailContent(
      String userName, String date, VerificationToken verificationToken) {
    Context context = new Context();
    context.setVariable("userName", userName);
    context.setVariable("verificationToken", verificationToken);
    context.setVariable("date", date);
    return templateEngine.process("email/account-verification", context);
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
    Long now = ZonedDateTime.now().toInstant().toEpochMilli();
    VerificationToken fetchedToken =
        verificationTokenRepository.findVerificationTokenByToken(verificationToken.getToken());
    if (fetchedToken != null) {
      if (fetchedToken.getExpiresAt() > now) {
        User user = fetchedToken.getUser();
        user.setEnabled(true);
        return userRepository.save(user);
      } else {
        sendEmailToVerifyUserAccount(fetchedToken.getUser());
        throw new UserNotVerifiedException();
      }
    }
    throw new UserNotVerifiedException();
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    User user = userRepository.findUserByEmail(email);
    if (user == null) {
      throw new EmailNotFoundException(email);
    }
    return new UserPrincipal(user);
  }
}
