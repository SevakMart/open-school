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
import java.time.ZonedDateTime;
import java.util.TimeZone;
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

  public AuthServiceImpl(
      UserRepository userRepository,
      BCryptPasswordEncoder passwordEncoder,
      CommunicationService communicationService,
      VerificationTokenRepository verificationTokenRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.communicationService = communicationService;
    this.verificationTokenRepository = verificationTokenRepository;
  }

  @Override
  public User register(UserRegistrationDto userDto, TimeZone timeZone) {

    if (emailAlreadyExist(userDto.getEmail())) {
      throw new EmailAlreadyExistException();
    }

    User user =
        userRepository.save(
            UserRegistrationMapper.userRegistrationDtoToUser(userDto, passwordEncoder));
    communicationService.sendEmailToVerifyUserAccount(user, timeZone);
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
  public User verifyAccount(VerificationToken verificationToken, TimeZone timeZone) {
    Long now = ZonedDateTime.now().toInstant().toEpochMilli();
    VerificationToken fetchedToken =
        verificationTokenRepository.findVerificationTokenByToken(verificationToken.getToken());
    if (fetchedToken != null) {
      if (fetchedToken.getExpiresAt() > now) {
        User user = fetchedToken.getUser();
        user.setEnabled(true);
        return userRepository.save(user);
      } else {
        communicationService.sendEmailToVerifyUserAccount(fetchedToken.getUser(), timeZone);
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
