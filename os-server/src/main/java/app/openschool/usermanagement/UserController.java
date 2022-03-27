package app.openschool.usermanagement;

import app.openschool.usermanagement.api.dto.MentorDto;
import app.openschool.usermanagement.api.dto.UserAuthRequest;
import app.openschool.usermanagement.api.dto.UserAuthResponse;
import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.api.dto.UserRegistrationHttpResponse;
import app.openschool.usermanagement.entities.User;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.util.Locale;

import static org.springframework.http.HttpStatus.CREATED;


@RestController
@RequestMapping("/api/v1")
public class UserController {

  public static final String SUCCESSFULLY_REGISTERED = " you've successfully registered";
  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;

  public UserController(UserService userService, BCryptPasswordEncoder passwordEncoder) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
  }

  @PostMapping("/register")
  @ResponseStatus(CREATED)
  @Operation(summary = "register students")
  public ResponseEntity<UserRegistrationHttpResponse> register(
      @Valid @RequestBody UserRegistrationDto userDto) {
    User user = userService.register(userDto);
    String message = user.getName() + SUCCESSFULLY_REGISTERED;
    UserRegistrationHttpResponse httpResponse =
        new UserRegistrationHttpResponse(message.toUpperCase(Locale.ROOT));

    return new ResponseEntity<>(httpResponse, CREATED);
  }

  @GetMapping("/mentors")
  @Operation(summary = "find all mentors")
  public ResponseEntity<Page<MentorDto>> findAllMentors(Pageable pageable) {
    return ResponseEntity.ok(this.userService.findAllMentors(pageable));
  }

  @PostMapping("/login")
  @Operation(summary = "login")
  public ResponseEntity<UserAuthResponse> login(
      @Valid @RequestBody UserAuthRequest userAuthRequest) {

    User userByEmail = userService.findUserByEmail(userAuthRequest.getEmail());
    if (userByEmail != null && passwordEncoder.matches(userAuthRequest.getPassword(),
        userByEmail.getPassword())) {
      return ResponseEntity.ok(userService.login(userByEmail));
    }
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
  }
}
