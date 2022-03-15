package app.openschool.usermanagement;

import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.api.dto.UserRegistrationHttpResponse;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Useful Javadoc. */
@RestController
@RequestMapping("/api")
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
