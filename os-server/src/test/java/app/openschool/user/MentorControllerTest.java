package app.openschool.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.auth.AuthServiceImpl;
import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.course.Course;
import app.openschool.user.api.UserGenerator;
import app.openschool.user.role.Role;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class MentorControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @MockBean private UserServiceImpl userService;

  @MockBean private AuthServiceImpl authService;

  @Test
  void findAllMentors_withUnauthenticatedUser_isUnauthorized() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/mentors")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findAllMentors_withLoggedUser_isOk() throws Exception {
    Pageable pageable = PageRequest.of(0, 2);
    Page<User> mentorPage = generateMentorPage(pageable);
    when(userService.findAllMentors(pageable)).thenReturn(mentorPage);

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            get("/api/v1/mentors")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void findMentorCourses() throws Exception {
    List<Course> mentorCourseList = new ArrayList<>();
    Page<Course> mentorCourseDtoPage = new PageImpl<>(mentorCourseList);
    when(userService.findMentorCourses(1L, null)).thenReturn(mentorCourseDtoPage);
    mockMvc
        .perform(get("/api/v1/mentors/1/courses").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findMentorsByName_withLoggedUser_isOk() throws Exception {
    String mentorName = "mentor";
    Pageable pageable = PageRequest.of(0, 2);
    PageImpl<User> mentorPage = generateMentorPage(pageable);
    when(userService.findMentorsByName(mentorName, pageable)).thenReturn(mentorPage);

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            get("/api/v1/mentors/searched")
                .queryParam("name", mentorName)
                .queryParam("page", "0")
                .queryParam("size", "2")
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void findMentorsByName_withUnauthenticatedUser_isUnauthorized() throws Exception {
    mockMvc
        .perform(
            get("/api/v1/mentors/searched")
                .queryParam("name", "mentor")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findSavedMentors_withCorrectUserId_isOk() throws Exception {
    Pageable pageable = PageRequest.of(0, 2);
    Page<User> mentorPage = generateMentorPage(pageable);
    when(authService.validateUserRequestAndReturnUser(anyLong())).thenReturn(new User());
    when(userService.findSavedMentors(any(), any())).thenReturn(mentorPage);

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            get("/api/v1/mentors/1")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void findSavedMentors_withIncorrectUserId_isBadRequest() throws Exception {
    when(authService.validateUserRequestAndReturnUser(anyLong()))
        .thenThrow(IllegalArgumentException.class);

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            get("/api/v1/mentors/1")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  @Test
  void findSavedMentorsByName_withCorrectUserId_isOk() throws Exception {
    Pageable pageable = PageRequest.of(0, 2);
    Page<User> mentorPage = generateMentorPage(pageable);
    when(authService.validateUserRequestAndReturnUser(anyLong())).thenReturn(new User());
    when(userService.findSavedMentorsByName(anyLong(), anyString(), any())).thenReturn(mentorPage);

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            get("/api/v1/mentors/searched/1")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .queryParam("name", "Mentor")
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void findSavedMentorsByName_withIncorrectUserId_isBadRequest() throws Exception {
    when(authService.validateUserRequestAndReturnUser(anyLong()))
        .thenThrow(IllegalArgumentException.class);

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            get("/api/v1/mentors/searched/1")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .queryParam("name", "Mentor")
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isBadRequest());
  }

  private PageImpl<User> generateMentorPage(Pageable pageable) {
    List<User> mentorList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      mentorList.add(UserGenerator.generateUser());
    }
    return new PageImpl<>(mentorList, pageable, 5);
  }

  private String generateJwtToken() {
    User user = new User("Test", "pass");
    user.setRole(new Role("STUDENT"));
    return "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
  }
}
