package app.openschool.usermanagement;

import static org.springframework.http.HttpStatus.CREATED;

import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.api.dto.UserRegistrationHttpResponse;
import app.openschool.usermanagement.entities.RoleEntity;
import app.openschool.usermanagement.entities.UserEntity;
import app.openschool.usermanagement.exceptions.EmailAlreadyExistException;
import java.util.Locale;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Useful Javadoc. */
@Service
public class UserServiceImpl implements UserService {

  public static final String EMAIL_ALREADY_EXISTS = "User with this email already exists";
  private final UserRepository userRepository;
  private final RoleRepository roleRepository;
  private final BCryptPasswordEncoder passwordEncoder;

  /** Useful Javadoc. */
  public UserServiceImpl(
      UserRepository userRepository,
      RoleRepository roleRepository,
      BCryptPasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  public ResponseEntity<UserRegistrationHttpResponse> register(UserRegistrationDto userDto) {

    if (emailAlreadyExist(userDto.getEmail())) {
      throw new EmailAlreadyExistException(EMAIL_ALREADY_EXISTS);
    }

    UserEntity user =
        new UserEntity(
            userDto.getFirstName(),
            userDto.getEmail(),
            passwordEncoder.encode(userDto.getPassword()));
    RoleEntity userRole = findRoleEntityByType("USER");
    user.setRole(userRole);
    userRepository.save(user);
    String message = user.getName() + " you've successfully registered";
    UserRegistrationHttpResponse httpResponse =
        new UserRegistrationHttpResponse(
            CREATED.value(), CREATED, message.toUpperCase(Locale.ROOT));
    return new ResponseEntity<>(httpResponse, CREATED);
  }

  @Override
  public UserEntity findUserByEmail(String email) {
    return userRepository.findUserByEmail(email);
  }

  @Override
  public RoleEntity findRoleEntityByType(String roleType) {
    return roleRepository.findRoleEntityByType(roleType);
  }

  private boolean emailAlreadyExist(String email) {
    return findUserByEmail(email) != null;
  }
}
