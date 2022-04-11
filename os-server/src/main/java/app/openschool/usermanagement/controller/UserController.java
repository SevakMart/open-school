package app.openschool.usermanagement.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import app.openschool.common.security.JwtTokenProvider;
import app.openschool.usermanagement.api.dto.MentorDto;
import app.openschool.usermanagement.api.dto.UserLoginDto;
import app.openschool.usermanagement.api.dto.UserLoginRequest;
import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.api.dto.UserRegistrationHttpResponse;
import app.openschool.usermanagement.entity.User;
import app.openschool.usermanagement.entity.UserPrincipal;
import app.openschool.usermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

  private static final String TOKEN_PREFIX = "Bearer ";
  public static final String JWT_TOKEN_HEADER = "Jwt-Token";

  private final UserService userService;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;
  private final MessageSource messageSource;

  public UserController(
      UserService userService,
      BCryptPasswordEncoder passwordEncoder,
      JwtTokenProvider jwtTokenProvider,
      AuthenticationManager authenticationManager,
      MessageSource messageSource) {
    this.userService = userService;
    this.passwordEncoder = passwordEncoder;
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
    this.messageSource = messageSource;
  }

  @PostMapping("/register")
  @ResponseStatus(CREATED)
  @Operation(summary = "register students")
  public ResponseEntity<UserRegistrationHttpResponse> register(
      @Valid @RequestBody UserRegistrationDto userDto, Locale locale) {
    User user = userService.register(userDto);
    String message =
        user.getName()
            + " "
            + messageSource.getMessage("response.register.successful.message", null, locale);
    UserRegistrationHttpResponse httpResponse =
        new UserRegistrationHttpResponse(message.toUpperCase(Locale.ROOT));

    return new ResponseEntity<>(httpResponse, CREATED);
  }

  @GetMapping("/mentors")
  @Operation(summary = "find all mentors", security = @SecurityRequirement(name = "bearerAuth"))
  public ResponseEntity<Page<MentorDto>> findAllMentors(Pageable pageable) {
    return ResponseEntity.ok(this.userService.findAllMentors(pageable));
  }

  @PostMapping("/login")
  @Operation(summary = "login")
  public ResponseEntity<UserLoginDto> login(@RequestBody UserLoginRequest userLoginRequest) {

    authenticate(userLoginRequest.getEmail(), userLoginRequest.getPassword());

    UserLoginDto userLoginDto = userService.login(userLoginRequest.getEmail());
    User loggedUser = userService.findUserByEmail(userLoginRequest.getEmail());
    UserPrincipal userPrincipal = new UserPrincipal(loggedUser);
    HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
    return new ResponseEntity<>(userLoginDto, jwtHeader, OK);
  }

  private void authenticate(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
  }

  private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(JWT_TOKEN_HEADER, TOKEN_PREFIX + jwtTokenProvider.generateJwtToken(userPrincipal));
    return headers;
  }
}
