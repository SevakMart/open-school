package app.openschool.user;

import static app.openschool.user.enums.UserRoles.USER;

import app.openschool.user.dto.UserRegistrationDto;
import app.openschool.user.entities.Company;
import app.openschool.user.entities.Role;
import app.openschool.user.entities.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Useful Javadoc. */
@Service
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;

  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  @Transactional
  public User register(UserRegistrationDto userDto) {
    User user = new User(userDto.getFirstName(), userDto.getEmail(), userDto.getPassword());
    Role role = new Role(USER.name());
    role.setUser (user);
    user.getUserRoles().add(role);
    return userRepository.save(user);
  }
}
