package app.openschool.user;

import app.openschool.user.dto.UserRegistrationDto;
import app.openschool.user.entities.User;

/** Useful Javadoc. */
public interface UserService {

  User register(UserRegistrationDto userDto);
}
