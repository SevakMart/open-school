package app.openschool.usermanagement;

import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.api.dto.UserRegistrationHttpResponse;
import app.openschool.usermanagement.entities.RoleEntity;
import app.openschool.usermanagement.entities.UserEntity;
import org.springframework.http.ResponseEntity;

/** Useful Javadoc. */
public interface UserService {

  ResponseEntity<UserRegistrationHttpResponse> register(UserRegistrationDto userDto);

  UserEntity findUserByEmail(String email);

  RoleEntity findRoleEntityByRoleType(String roleType);
}
