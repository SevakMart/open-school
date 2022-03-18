package app.openschool.usermanagement;

import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.entities.User;

public interface UserService {

  User register(UserRegistrationDto userDto);

  User findUserByEmail(String email);
}
