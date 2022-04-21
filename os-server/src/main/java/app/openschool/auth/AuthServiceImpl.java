package app.openschool.auth;

import app.openschool.auth.dto.UserLoginDto;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.auth.exception.EmailAlreadyExistException;
import app.openschool.auth.exception.EmailNotFoundException;
import app.openschool.auth.exception.NotMatchingPasswordsException;
import app.openschool.auth.mapper.UserLoginMapper;
import app.openschool.auth.mapper.UserRegistrationMapper;
import app.openschool.common.security.UserPrincipal;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.api.exception.UserNotFoundException;
import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import net.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.mail.javamail.JavaMailSender;

@Service
public class AuthServiceImpl implements AuthService, UserDetailsService {

  private final UserRepository userRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  @Autowired JavaMailSender mailSender;

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

  public void sendEmail(String recipientEmail, String link)
      throws MessagingException, UnsupportedEncodingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);

    helper.setFrom("open_school_gmail.com", "Open_School Support");
    helper.setTo(recipientEmail);

    String subject = "Here's the link to reset your password";

    String content =
        "<p>Hello,</p>"
            + "<p>You have requested to reset your password.</p>"
            + "<p>Click the link below to change your password:</p>"
            + "<p><a href=\""
            + link
            + "\">Change my password</a></p>"
            + "<br>"
            + "<p>Ignore this email if you remember your password, "
            + "or you have not made the request.</p>";

    helper.setSubject(subject);

    helper.setText(content, true);

    mailSender.send(message);
  }

  public void updateResetPasswordToken(String email)
      throws MessagingException, UnsupportedEncodingException {

    User user = userRepository.findUserByEmail(email);
    if (user == null) {
      throw new EmailNotFoundException(email);
    }
    String token = RandomString.make(45);
    user.setResetPasswordToken(token);
    userRepository.save(user);
    String resetPasswordLink = "localhost:5000/reset-password?token=" + token;
    sendEmail(email, resetPasswordLink);
  }

  public void updatePassword(String token, String newPassword, String confirmedPassword) {

    if (!newPassword.equals(confirmedPassword)) {
      throw new NotMatchingPasswordsException("Passwords do not match");
    }
    User user = userRepository.findByResetPasswordToken(token);
    if (user == null) {
      throw new UserNotFoundException("Invalid Token");
    }
    String encodedPassword = passwordEncoder.encode(newPassword);
    user.setPassword(encodedPassword);
    user.setResetPasswordToken(null);
    userRepository.save(user);
  }
}
