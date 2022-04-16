package app.openschool.usermanagement;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.user.User;
import app.openschool.user.UserPrincipal;
import app.openschool.user.UserServiceImpl;
import app.openschool.user.api.dto.MentorDto;
import app.openschool.user.api.dto.UserRegistrationDto;
import app.openschool.user.api.mapper.MentorMapper;
import app.openschool.user.role.Role;
import app.openschool.usermanagement.api.UserGenerator;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;



@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private UserServiceImpl userService;

  @MockBean AuthenticationManager authenticationManager;

  @Test
  void registerValidUser() throws Exception {
    when(userService.register(any(UserRegistrationDto.class))).thenReturn(new User());

    String requestBody =
        "{ \"firstName\": \"Test\",\"email\": \"test@gmail.com\",\"password\": \"1234$dhjsHH*\" }";
    mockMvc
        .perform(post("/api/v1/register").contentType(APPLICATION_JSON).content(requestBody))
        .andExpect(status().isCreated());
  }

  @Test
  void registerInvalidUserName() throws Exception {
    when(userService.register(any(UserRegistrationDto.class))).thenReturn(new User());

    String requestBody =
        "{ \"firstName\": \"\",\"email\": \"test@gmail.com\",\"password\": \"1234$dhjsHH*\" }";
    mockMvc
        .perform(post("/api/v1/register").contentType(APPLICATION_JSON).content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  void registerInvalidUserPassword() throws Exception {
    when(userService.register(any(UserRegistrationDto.class))).thenReturn(new User());

    String requestBody =
        "{ \"firstName\": \"Test\",\"email\": \"test@gmail.com\",\"password\": \"1234$dhjshh*\" }";
    mockMvc
        .perform(post("/api/v1/register").contentType(APPLICATION_JSON).content(requestBody))
        .andExpect(status().isBadRequest());
  }

  @Test
  void findAllMentors() throws Exception {
    List<MentorDto> mentorDtoList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      mentorDtoList.add(MentorMapper.toMentorDto(UserGenerator.generateUser()));
    }
    Pageable pageable = PageRequest.of(0, 2);
    Page<MentorDto> mentorPage = new PageImpl<>(mentorDtoList, pageable, 5);
    when(userService.findAllMentors(pageable)).thenReturn(mentorPage);
    mockMvc
        .perform(
            get("/api/v1/mentors")
                .queryParam("page", "1")
                .queryParam("size", "2")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void loginWithWrongCredentials() throws Exception {
    User user = new User("test@gmail.com", "1234$dhjshh*");
    when(authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())))
        .thenThrow(BadCredentialsException.class);

    String requestBody = "{ \"email\": \"test@gmail.com\",\"password\": \"1234$dhjshh*\" }";

    mockMvc
        .perform(post("/api/v1/login").contentType(APPLICATION_JSON).content(requestBody))
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

    when(userService.findUserByEmail("test@gmail.com")).thenReturn(user);

    String requestBody = "{ \"email\": \"test@gmail.com\",\"password\": \"1234$dhjshh*\" }";

    mockMvc
        .perform(post("/api/v1/login").contentType(APPLICATION_JSON).content(requestBody))
        .andExpect(status().isOk());
  }
}
