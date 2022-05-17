package app.openschool.auth;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import app.openschool.auth.dto.ResetPasswordRequest;
import app.openschool.auth.dto.UserLoginDto;
import app.openschool.auth.dto.UserLoginRequest;
import app.openschool.auth.dto.UserRegistrationDto;
import app.openschool.auth.dto.UserRegistrationHttpResponse;
import app.openschool.auth.verification.VerificationToken;
import app.openschool.common.response.ResponseMessage;
import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.user.User;
import io.swagger.v3.oas.annotations.Operation;
import java.util.Locale;
import javax.validation.Valid;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  private static final String TOKEN_PREFIX = "Bearer ";
  private static final String JWT_TOKEN_HEADER = "Authorization";

  private final JwtTokenProvider jwtTokenProvider;
  private final AuthenticationManager authenticationManager;
  private final MessageSource messageSource;
  private final AuthService authService;
  private final ITemplateEngine templateEngine;

  public AuthController(
      JwtTokenProvider jwtTokenProvider,
      AuthenticationManager authenticationManager,
      MessageSource messageSource,
      AuthService authService,
      ITemplateEngine templateEngine) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
    this.messageSource = messageSource;
    this.authService = authService;
    this.templateEngine = templateEngine;
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
        new UserRegistrationHttpResponse(user.getId(), message.toUpperCase(Locale.ROOT));
    return ResponseEntity.status(CREATED).body(httpResponse);
  }

  @PostMapping("/login")
  @Operation(summary = "login")
  public ResponseEntity<UserLoginDto> login(@RequestBody UserLoginRequest userLoginRequest) {

    authenticate(userLoginRequest.getEmail(), userLoginRequest.getPassword());

    UserLoginDto userLoginDto = authService.login(userLoginRequest.getEmail());
    User loggedUser = authService.findUserByEmail(userLoginRequest.getEmail());
    UserPrincipal userPrincipal = new UserPrincipal(loggedUser);
    HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
    return ResponseEntity.ok().headers(jwtHeader).body(userLoginDto);
  }

  @GetMapping("/account/verification")
  public String verifyAccount(@ModelAttribute VerificationToken verificationToken, Locale locale) {
    User user = authService.verifyAccount(verificationToken);
    String[] args = {user.getName()};
    String message = messageSource.getMessage("verification.success.message", args, locale);
    Context context = new Context();
    context.setVariable("message", message);
    return templateEngine.process("verification-response", context);
  }

  @GetMapping("/{userId}/account/verification")
  public ResponseEntity<Void> resendVerificationEmail(@PathVariable Long userId) {
    authService.sendVerificationEmail(userId);
    return ResponseEntity.ok().build();
  }

  private void authenticate(String username, String password) {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
  }

  private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
    HttpHeaders headers = new HttpHeaders();
    headers.add(JWT_TOKEN_HEADER, TOKEN_PREFIX + jwtTokenProvider.generateJwtToken(userPrincipal));
    return headers;
  }

  @PostMapping("/password/forgot")
  @Operation(summary = "send token to given email")
  public ResponseEntity<ResponseMessage> forgotPassword(@RequestBody String email, Locale locale) {
    authService.updateResetPasswordToken(email);
    return ResponseEntity.status(OK)
        .body(
            new ResponseMessage(
                messageSource.getMessage("password.sending.success.message", null, locale)));
  }

  @PostMapping("/password/reset")
  @Operation(summary = "reset password")
  public ResponseEntity<ResponseMessage> resetPassword(
      @Valid @RequestBody ResetPasswordRequest request, Locale locale) {
    authService.resetPassword(request);
    return ResponseEntity.status(OK)
        .body(
            new ResponseMessage(
                messageSource.getMessage("password.reset.success.message", null, locale)));
  }
}
