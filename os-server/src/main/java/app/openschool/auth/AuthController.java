package app.openschool.auth;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import app.openschool.auth.api.dto.ForgotPasswordRequest;
import app.openschool.auth.api.dto.ResetPasswordRequest;
import app.openschool.auth.api.dto.UserLoginDto;
import app.openschool.auth.api.dto.UserLoginExceptionResponse;
import app.openschool.auth.api.dto.UserLoginRequest;
import app.openschool.auth.api.dto.UserRegistrationDto;
import app.openschool.auth.api.dto.UserRegistrationResponse;
import app.openschool.auth.api.mapper.UserRegistrationMapper;
import app.openschool.auth.entity.ResetPasswordToken;
import app.openschool.auth.verification.api.dto.VerificationTokenDto;
import app.openschool.common.response.ResponseMessage;
import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.user.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
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

  @Operation(summary = "register students")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description =
                "User has been registered and account verification email "
                    + "has been sent to the provided email",
            content = @Content(schema = @Schema(implementation = UserRegistrationResponse.class))),
        @ApiResponse(
            responseCode = "400",
            description =
                "If name not provided or contains only whitespace, "
                    + "if password not provided, contains only whitespace "
                    + "or provided password doesn't match to password pattern, "
                    + "if email not provided, contains only whitespace, "
                    + "provided email doesn't match to email pattern "
                    + "or exists a user registered with provided email",
            content = {
              @Content(
                  array = @ArraySchema(schema = @Schema(implementation = String.class)),
                  examples = {
                    @ExampleObject("[\"Email is mandatory\", \"Invalid Password format\"]")
                  })
            })
      })
  @PostMapping("/register")
  public ResponseEntity<?> register(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object which contains provided name, email and password")
          @Valid
          @RequestBody
          UserRegistrationDto userDto,
      BindingResult bindingResult,
      Locale locale) {
    if (authService.emailAlreadyExist(userDto.getEmail())) {
      bindingResult.rejectValue(
          "email",
          "error.user",
          messageSource.getMessage("exception.duplicate.user.email.message", null, locale));
    }
    if (bindingResult.hasErrors()) {
      return ResponseEntity.badRequest()
          .body(
              bindingResult.getAllErrors().stream()
                  .map(DefaultMessageSourceResolvable::getDefaultMessage));
    }
    return ResponseEntity.status(CREATED)
        .body(UserRegistrationMapper.toUserRegistrationResponse(authService.register(userDto)));
  }

  @Operation(summary = "login")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "User logged in"),
        @ApiResponse(
            responseCode = "401",
            description =
                "If provided email or password is invalid or user's account isn't verified",
            content = @Content(schema = @Schema(implementation = UserLoginExceptionResponse.class)))
      })
  @PostMapping("/login")
  public ResponseEntity<UserLoginDto> login(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description = "Request object which contains provided email and password")
          @RequestBody
          UserLoginRequest userLoginRequest) {
    authenticate(userLoginRequest.getEmail(), userLoginRequest.getPassword());
    UserLoginDto userLoginDto = authService.login(userLoginRequest.getEmail());
    User loggedUser = authService.findUserByEmail(userLoginRequest.getEmail());
    UserPrincipal userPrincipal = new UserPrincipal(loggedUser);
    HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
    return ResponseEntity.ok().headers(jwtHeader).body(userLoginDto);
  }

  @Operation(summary = "verify account")
  @ApiResponse(responseCode = "200", description = "Account is verified")
  @GetMapping("/account/verification")
  public String verifyAccount(
      @ModelAttribute VerificationTokenDto verificationTokenDto, Locale locale) {
    User user = authService.verifyAccount(verificationTokenDto.getToken());
    String[] args = {user.getName()};
    String message = messageSource.getMessage("verification.success.message", args, locale);
    Context context = new Context();
    context.setVariable("message", message);
    return templateEngine.process("verification-response", context);
  }

  @Operation(summary = "resend email for account verification")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "The email has been resent to provided email",
            content = @Content(schema = @Schema())),
        @ApiResponse(
            responseCode = "400",
            description = "Invalid userId supplied",
            content = @Content(schema = @Schema(implementation = ResponseMessage.class)))
      })
  @GetMapping("/{userId}/account/verification")
  public ResponseEntity<Void> resendVerificationEmail(
      @Parameter(description = "User's id whom will be sent a verification email") @PathVariable
          Long userId) {
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
                "If provided email is null, blank, doesn't match to "
                    + "email pattern or user not found by provided email",
            content = @Content(schema = @Schema(oneOf = {ResponseMessage.class, Stream.class})))
      })
  @PostMapping("/password/forgot")
  public ResponseEntity<?> forgotPassword(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description =
                  "Request object which contains provided email address where will be sent token")
          @Valid
          @RequestBody
          ForgotPasswordRequest forgotPasswordRequest,
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
                "If provided token isn't valid, is expired, provided password doesn't match to "
                    + "password pattern, or password and confirmed password doesn't match",
            content = @Content(schema = @Schema(oneOf = {ResponseMessage.class, Stream.class})))
      })
  @PostMapping("/password/reset")
  public ResponseEntity<?> resetPassword(
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
              description =
                  "Request object which contains token, new password and confirmed password")
          @Valid
          @RequestBody
          ResetPasswordRequest request,
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
