package app.openschool.usermanagement.controller;

import static org.springframework.http.HttpStatus.CREATED;

import app.openschool.usermanagement.api.dto.MentorDto;
import app.openschool.usermanagement.api.dto.UserLoginRequest;
import app.openschool.usermanagement.api.dto.UserLoginResponse;
import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.api.dto.UserRegistrationHttpResponse;
import app.openschool.usermanagement.entity.User;
import app.openschool.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class UserController {

  public static final String SUCCESSFULLY_REGISTERED = " you've successfully registered";

  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;
  private final AuthenticationManager authenticationManager;

  public UserController(
      UserService userService,
      BCryptPasswordEncoder passwordEncoder,
      AuthenticationManager authenticationManager) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.authenticationManager = authenticationManager;
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
  public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {

    authenticate(userLoginRequest.getEmail(), userLoginRequest.getPassword());

    return ResponseEntity.ok(userService.login(userLoginRequest.getEmail()));
  }

  private void authenticate(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
  }
}
