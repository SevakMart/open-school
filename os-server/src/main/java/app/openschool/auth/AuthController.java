package app.openschool.auth;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import app.openschool.auth.dto.UserLoginDto;
import app.openschool.auth.dto.UserLoginRequest;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.auth.dto.UserRegistrationHttpResponse;
import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.user.User;
import app.openschool.user.api.exception.UserNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import java.net.URI;
import java.util.Locale;
import javax.validation.Valid;
import net.bytebuddy.utility.RandomString;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private static final String TOKEN_PREFIX = "Bearer ";
  public static final String JWT_TOKEN_HEADER = "Authorization";

  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;
  private final MessageSource messageSource;
  private final AuthService authService;

  public AuthController(
      JwtTokenProvider jwtTokenProvider,
      AuthenticationManager authenticationManager,
      MessageSource messageSource,
      AuthService authService) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
    this.messageSource = messageSource;
    this.authService = authService;
  }

  @PostMapping("/register")
  @ResponseStatus(CREATED)
  @Operation(summary = "register students")
  public ResponseEntity<UserRegistrationHttpResponse> register(
          @Valid @RequestBody UserRegistrationDto userDto, Locale locale) {
    User user = authService.register(userDto);
    String message =
        user.getName()
            + " "
            + messageSource.getMessage("response.register.successful.message", null, locale);
    UserRegistrationHttpResponse httpResponse =
        new UserRegistrationHttpResponse(message.toUpperCase(Locale.ROOT));

    return new ResponseEntity<>(httpResponse, CREATED);
  }

  @PostMapping("/login")
  @Operation(summary = "login")
  public ResponseEntity<UserLoginDto> login(@RequestBody UserLoginRequest userLoginRequest) {

    authenticate(userLoginRequest.getEmail(), userLoginRequest.getPassword());

    UserLoginDto userLoginDto = authService.login(userLoginRequest.getEmail());
    User loggedUser = authService.findUserByEmail(userLoginRequest.getEmail());
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

  @PostMapping("/forgot-password")
  public ResponseEntity<HttpStatus> forgotPassword(HttpRequest httpRequest, String email) {
    try {
      authService.updateResetPasswordToken(httpRequest, email);
    } catch (UserNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();
  }

  @PostMapping("/reset_password")
  public ResponseEntity<> processResetPassword(String token, String password, String confirmedPassword) {

    try {
      authService.updatePassword(token, password, confirmedPassword);
    } catch (UserNotFoundException ex) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).build();}
    return ResponseEntity.status(HttpStatus.ACCEPTED).build();

    User user = authService.getByResetPasswordToken(token);

    if (user == null) {
      model.addAttribute("message", "Invalid Token");
      return "message";
    } else {
      customerService.updatePassword(customer, password);

      model.addAttribute("message", "You have successfully changed your password.");
    }

    return "message";
  }
}
