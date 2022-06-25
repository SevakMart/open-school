package app.openschool.auth;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import app.openschool.auth.api.dto.ForgotPasswordRequest;
import app.openschool.auth.api.dto.ResetPasswordRequest;
import app.openschool.auth.api.dto.UserLoginDto;
import app.openschool.auth.api.dto.UserLoginRequest;
import app.openschool.auth.api.dto.UserRegistrationDto;
import app.openschool.auth.api.dto.UserRegistrationHttpResponse;
import app.openschool.auth.entity.ResetPasswordToken;
import app.openschool.auth.verification.VerificationToken;
import app.openschool.common.response.ResponseMessage;
import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.validation.BindingResult;
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
  private final Integer tokenExpirationAfterMinutes;
  private final ITemplateEngine templateEngine;

  public AuthController(
      JwtTokenProvider jwtTokenProvider,
      AuthenticationManager authenticationManager,
      MessageSource messageSource,
      AuthService authService,
      @Value("${token.expiration}") Integer tokenExpirationAfterMinutes,
      ITemplateEngine templateEngine) {
    this.jwtTokenProvider = jwtTokenProvider;
    this.authenticationManager = authenticationManager;
    this.messageSource = messageSource;
    this.authService = authService;
    this.tokenExpirationAfterMinutes = tokenExpirationAfterMinutes;
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

  @Operation(summary = "send token to given email")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "When token have been send to provided email address",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "400",
            description =
                "If provided email is null, blank, doesn't match to email pattern or user not found by provided email",
            content = @Content(schema = @Schema(oneOf = {ResponseMessage.class, Stream.class})))
      })
  @PostMapping("/password/forgot")
  public ResponseEntity<?> forgotPassword(
      @Parameter(
              description =
                  "Request object which contains provided email address where will be sent token")
          @Valid
          @RequestBody
          ForgotPasswordRequest forgotPasswordRequest,
      @Parameter(
              description =
                  "Object which may contain errors depending on passed null value, only whitespace "
                      + "or invalid email to the email field of forgotPasswordRequest")
          BindingResult bindingResult,
      Locale locale) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest()
          .body(
              bindingResult.getAllErrors().stream()
                  .map(DefaultMessageSourceResolvable::getDefaultMessage));
    }
    String email = forgotPasswordRequest.getEmail();
    Optional<User> optionalUser = authService.findByEmail(email);
    if (optionalUser.isEmpty()) {
      String[] args = {email};
      return ResponseEntity.badRequest()
          .body(
              new ResponseMessage(
                  messageSource.getMessage(
                      "exception.nonexistent.user.email.message", args, locale)));
    }
    authService.updateResetPasswordToken(email, optionalUser.get());
    return ResponseEntity.status(OK)
        .body(
            new ResponseMessage(
                messageSource.getMessage("password.sending.success.message", null, locale)));
  }

  @Operation(summary = "reset password")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "When password has been changed",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class))),
        @ApiResponse(
            responseCode = "400",
            description =
                "If provided token isn't valid, is expired, provided password doesn't match to password pattern, "
                    + "or password and confirmed password doesn't match",
            content = @Content(schema = @Schema(oneOf = {ResponseMessage.class, Stream.class})))
      })
  @PostMapping("/password/reset")
  public ResponseEntity<?> resetPassword(
      @Parameter(
              description =
                  "Request object which contains token, new password and confirmed password")
          @Valid
          @RequestBody
          ResetPasswordRequest request,
      @Parameter(
              description =
                  "Object which may contain errors depending on passed null value, only whitespace "
                      + "or invalid values to the fields of request")
          BindingResult bindingResult,
      Locale locale) {
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest()
          .body(
              bindingResult.getAllErrors().stream()
                  .map(DefaultMessageSourceResolvable::getDefaultMessage));
    }
    Optional<ResetPasswordToken> optionalResetPasswordToken =
        authService.findByToken(request.getToken());
    if (optionalResetPasswordToken.isEmpty()) {
      return ResponseEntity.badRequest()
          .body(new ResponseMessage(messageSource.getMessage("token.not.valid", null, locale)));
    }
    ResetPasswordToken resetPasswordToken = optionalResetPasswordToken.get();
    if (resetPasswordToken.isExpired(tokenExpirationAfterMinutes)) {
      return ResponseEntity.badRequest()
          .body(new ResponseMessage(messageSource.getMessage("token.expired", null, locale)));
    }
    authService.resetPassword(request, resetPasswordToken);
    return ResponseEntity.status(OK)
        .body(
            new ResponseMessage(
                messageSource.getMessage("password.reset.success.message", null, locale)));
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
