package app.openschool.user;

import app.openschool.user.dto.UserRegistrationDto;
import app.openschool.user.dto.UserRegistrationHttpResponse;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/** Useful Javadoc. */
@RestController
public class UserController {

  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /** Useful Javadoc. */
  @PostMapping("/register")
  public ResponseEntity<UserRegistrationHttpResponse> register(
      @Valid @RequestBody UserRegistrationDto userDto) {
    return userService.register(userDto);
  }
}
