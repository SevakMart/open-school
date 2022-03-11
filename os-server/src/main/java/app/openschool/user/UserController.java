package app.openschool.user;

import app.openschool.user.dto.UserRegistrationDto;
import app.openschool.user.entities.User;
import org.springframework.http.HttpStatus;
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
  public ResponseEntity<User> register(@RequestBody UserRegistrationDto userDto) {

    User registeredUser = userService.register(userDto);

    return new ResponseEntity<>(registeredUser, HttpStatus.OK);
  }
}
