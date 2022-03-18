package app.openschool.usermanagement;

import static org.springframework.http.HttpStatus.CREATED;

import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.api.dto.UserRegistrationHttpResponse;
import app.openschool.usermanagement.entities.User;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

  public static final String SUCCESSFULLY_REGISTERED = " you've successfully registered";
  private final UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  @PostMapping("/register")
  public ResponseEntity<UserRegistrationHttpResponse> register(
      @Valid @RequestBody UserRegistrationDto userDto) {
    User user = userService.register(userDto);
    String message = user.getName() + SUCCESSFULLY_REGISTERED;
    UserRegistrationHttpResponse httpResponse =
        new UserRegistrationHttpResponse(CREATED, message.toUpperCase(Locale.ROOT));

    return new ResponseEntity<>(httpResponse, CREATED);
  }
}
