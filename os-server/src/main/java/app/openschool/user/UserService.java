package app.openschool.user;

import app.openschool.user.dto.UserRegistrationDto;
import app.openschool.user.dto.UserRegistrationHttpResponse;
import app.openschool.user.entities.User;
import org.springframework.http.ResponseEntity;

/** Useful Javadoc. */
public interface UserService {

  ResponseEntity<UserRegistrationHttpResponse> register(UserRegistrationDto userDto);

  User findUserByEmail(String email);
}
