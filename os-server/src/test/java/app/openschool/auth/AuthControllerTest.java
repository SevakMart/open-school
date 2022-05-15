package app.openschool.auth;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.auth.api.dto.UserRegistrationDto;
import app.openschool.auth.entity.ResetPasswordToken;
import app.openschool.common.security.UserPrincipal;
import app.openschool.user.User;
import app.openschool.user.role.Role;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private AuthServiceImpl authService;

  @MockBean AuthenticationManager authenticationManager;

  @Test
  void registerValidUser() throws Exception {
    when(authService.register(any(UserRegistrationDto.class))).thenReturn(new User());

    String requestBody =
        "{ \"firstName\": \"Test\",\"email\": \"test@gmail.com\",\"password\": \"1234$dhjsHH*\" }";
    mockMvc
        .perform(post("/api/v1/auth/register").contentType(APPLICATION_JSON).content(requestBody))
        .andExpect(status().isCreated());
  }

  @Test
  void registerInvalidUserName() throws Exception {
    when(authService.register(any(UserRegistrationDto.class))).thenReturn(new User());

    String requestBody =
        "{ \"firstName\": \"\",\"email\": \"test@gmail.com\",\"password\": \"1234$dhjsHH*\" }";
    mockMvc
        .perform(post("/api/v1/auth/register").contentType(APPLICATION_JSON).content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  void registerInvalidUserPassword() throws Exception {
    when(authService.register(any(UserRegistrationDto.class))).thenReturn(new User());

    String requestBody =
        "{ \"firstName\": \"Test\",\"email\": \"test@gmail.com\",\"password\": \"1234$dhjshh*\" }";
    mockMvc
        .perform(post("/api/v1/auth/register").contentType(APPLICATION_JSON).content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  void loginWithWrongCredentials() throws Exception {
    User user = new User("test@gmail.com", "1234$dhjshh*");
    when(authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())))
        .thenThrow(BadCredentialsException.class);

    String requestBody = "{ \"email\": \"test@gmail.com\",\"password\": \"1234$dhjshh*\" }";

    mockMvc
        .perform(post("/api/v1/auth/login").contentType(APPLICATION_JSON).content(requestBody))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void loginWithRightCredentials() throws Exception {
    User user = new User("test@gmail.com", "1234$dhjshh*");
    user.setRole(new Role("STUDENT"));
    UserPrincipal userPrincipal = new UserPrincipal(user);
    when(authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())))
        .thenReturn(new UsernamePasswordAuthenticationToken(null, null, null));

    when(authService.findUserByEmail("test@gmail.com")).thenReturn(user);

    String requestBody = "{ \"email\": \"test@gmail.com\",\"password\": \"1234$dhjshh*\" }";

    mockMvc
        .perform(post("/api/v1/auth/login").contentType(APPLICATION_JSON).content(requestBody))
        .andExpect(status().isOk());
  }

  @Test
  void forgotPassword() throws Exception {
    User user = new User();
    when(authService.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
    mockMvc
        .perform(
            post("/api/v1/auth/password/forgot")
                .contentType(APPLICATION_JSON)
                .content("test@gmail.com"))
        .andExpect(status().isOk());
  }

  @Test
  void resetPassword() throws Exception {
    ResetPasswordToken resetPasswordToken = ResetPasswordToken.generate(new User());
    when(authService.findByToken("7478")).thenReturn(Optional.of(resetPasswordToken));
    String requestBody =
        "{ \"token\": \"7478\",\"newPassword\": \"Test7777$\","
            + "\"confirmedPassword\": \"Test7777$\" }";
    mockMvc
        .perform(
            post("/api/v1/auth/password/reset").contentType(APPLICATION_JSON).content(requestBody))
        .andExpect(status().isOk());
  }
}
