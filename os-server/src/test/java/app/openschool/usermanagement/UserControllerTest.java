package app.openschool.usermanagement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.common.security.JwtAuthenticationEntryPoint;
import app.openschool.usermanagement.api.dto.UserAuthRequest;
import app.openschool.usermanagement.api.dto.UserAuthResponse;
import app.openschool.usermanagement.api.dto.UserRegistrationDto;
import app.openschool.usermanagement.entities.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @Test
  void registerValidUser() throws Exception {
    when(userService.register(any(UserRegistrationDto.class))).thenReturn(new User());

    String requestBody =
        "{ \"firstName\": \"Test\",\"email\": \"test@gmail.com\",\"password\": \"1234$dhjsHH*\" }";
    mockMvc
        .perform(
            post("/api/v1/register").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isCreated());
  }

  @Test
  void registerInvalidUserName() throws Exception {
    when(userService.register(any(UserRegistrationDto.class))).thenReturn(new User());

    String requestBody =
        "{ \"firstName\": \"\",\"email\": \"test@gmail.com\",\"password\": \"1234$dhjsHH*\" }";
    mockMvc
        .perform(
            post("/api/v1/register").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  void registerInvalidUserPassword() throws Exception {
    when(userService.register(any(UserRegistrationDto.class))).thenReturn(new User());

    String requestBody =
        "{ \"firstName\": \"Test\",\"email\": \"test@gmail.com\",\"password\": \"1234$dhjshh*\" }";
    mockMvc
        .perform(
            post("/api/v1/register").contentType(MediaType.APPLICATION_JSON).content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  void loginValidPasswordAndEmail() throws Exception {
    when(userService.login(any(User.class))).thenReturn(new UserAuthResponse());

    String requestBody =
        "{\"firstName\": \"userName\" ,"
            + " \"email\": \"email@mail.com\" ,"
            + " \"password\": \"Password_1\" }";

    mockMvc
        .perform(
            post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isOk());
  }

  @Test
  void loginInvalidPassword() throws Exception {
    when(userService.login(any(User.class))).thenReturn(new UserAuthResponse());

    String requestBody =
        "{\"firstName\": \"userName\" ,"
            + " \"email\": \"email@mail.com\" ,"
            + " \"password\": \"\" }";

    mockMvc
        .perform(
            post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void loginInvalidEmail() throws Exception {
    when(userService.login(any(User.class))).thenReturn(new UserAuthResponse());

    String requestBody =
        "{\"firstName\": \"userName\" ,"
            + " \"email\": \"\" ,"
            + " \"password\": \"Password_1\" }";

    mockMvc
        .perform(
            post("/api/v1/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody))
        .andExpect(status().isUnauthorized());
  }
}
