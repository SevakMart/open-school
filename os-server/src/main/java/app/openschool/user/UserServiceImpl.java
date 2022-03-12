package app.openschool.user;

import static app.openschool.user.enums.UserRoles.USER;
import static org.springframework.http.HttpStatus.CREATED;

import app.openschool.user.dto.UserRegistrationDto;
import app.openschool.user.dto.UserRegistrationHttpResponse;
import app.openschool.user.entities.User;
import app.openschool.user.entities.UserRole;
import app.openschool.user.exceptions.EmailAlreadyExistException;
import java.util.Locale;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Useful Javadoc. */
@Service
public class UserServiceImpl implements UserService {

  public static final String EMAIL_ALREADY_EXISTS = "User with this email already exists";
  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public ResponseEntity<UserRegistrationHttpResponse> register(UserRegistrationDto userDto) {

    if (emailAlreadyExist(userDto.getEmail())) {
      throw new EmailAlreadyExistException(EMAIL_ALREADY_EXISTS);
    }

    User user = new User(userDto.getFirstName(), userDto.getEmail(), userDto.getPassword());
    UserRole userRole = new UserRole(USER.name(), user);
    user.getUserRoles().add(userRole);
    userRepository.save(user);
    String message = user.getFirstName() + " you've successfully registered";
    UserRegistrationHttpResponse httpResponse =
        new UserRegistrationHttpResponse(
            CREATED.value(), CREATED, message.toUpperCase(Locale.ROOT));
    return new ResponseEntity<UserRegistrationHttpResponse>(httpResponse, CREATED);
  }

  @Override
  public User findUserByEmail(String email) {
    return userRepository.findUserByEmail(email);
  }

  private boolean emailAlreadyExist(String email) {
    return findUserByEmail(email) != null;
  }
}
