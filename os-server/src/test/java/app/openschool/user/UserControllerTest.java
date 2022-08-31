package app.openschool.user;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.auth.AuthServiceImpl;
import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.CourseGenerator;
import app.openschool.course.api.dto.EnrolledCourseOverviewDto;
import app.openschool.course.api.mapper.EnrolledCourseMapper;
import app.openschool.user.role.Role;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @MockBean private UserServiceImpl userService;

  @MockBean private AuthServiceImpl authService;

  @Test
  void getSuggestedCourses() throws Exception {
    List<Course> courseList = new ArrayList<>();
    when(userService.getSuggestedCourses(1L)).thenReturn(courseList);
    mockMvc
        .perform(get("/api/v1/users/1/courses/suggested").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findEnrolledUserCourses() throws Exception {
    List<EnrolledCourse> userEnrolledCourseList = new ArrayList<>();
    when(userService.findEnrolledCourses(1L, null)).thenReturn(userEnrolledCourseList);
    mockMvc
        .perform(get("/api/v1/users/1/courses/enrolled").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void savePreferredCategories() throws Exception {
    String requestBody = "{  \"categoriesIdSet\": [ 2, 3 ] }";
    mockMvc
        .perform(
            post("/api/v1/users/1/categories").content(requestBody).contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void enrollCourse_UnauthorizedRequest_isUnauthorized() throws Exception {
    mockMvc
        .perform(post("/api/v1/users/2/courses/1").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void enrollCourse_WithWrongCourseId_isBadRequest() throws Exception {
    when(userService.enrollCourse(any(), anyLong())).thenThrow(IllegalArgumentException.class);

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            post("/api/v1/users/2/courses/1")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isBadRequest());
  }

  @Test
  void enrollCourse_withRightCourseId_isCreated() throws Exception {
    when(userService.enrollCourse(any(), anyLong())).thenReturn(CourseGenerator.generateCourse());

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            post("/api/v1/users/2/courses/1")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isCreated());
  }

  @Test
  void enrollCourse_WithWrongUserId_isBadRequest() throws Exception {
    when(authService.validateUserRequestAndReturnUser(anyLong()))
        .thenThrow(IllegalArgumentException.class);

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            post("/api/v1/users/2/courses/1")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isBadRequest());
  }

  @Test
  void findEnrolledCoursesOverview_userIsAuthenticated_expectStatusOk() throws Exception {
    mockStatic(EnrolledCourseMapper.class);
    when(EnrolledCourseMapper.toEnrolledCourseOverviewDto(any()))
        .thenReturn(new EnrolledCourseOverviewDto());
    when(userService.findEnrolledCourseById(any())).thenReturn(new EnrolledCourse());
    User user = new User("Test", "pass");
    user.setRole(new Role("STUDENT"));
    String jwt = "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
    mockMvc
        .perform(
            get("/api/v1/users/1/courses/enrolled/1")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isOk());
  }

  @Test
  void findEnrolledCoursesOverview_userIsNotAuthenticated_expectStatusUnauthorized()
      throws Exception {
    mockMvc
        .perform(get("/api/v1/users/1/courses/enrolled/1").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void saveMentor_WithCorrectCredentials_isCreated() throws Exception {
    when(authService.validateUserRequestAndReturnUser(anyLong())).thenReturn(new User());
    when(userService.saveMentor(any(), anyLong())).thenReturn(new User());

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            post("/api/v1/users/1/mentors/2")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isCreated());
  }

  @Test
  void saveMentor_withIncorrectUserId_isBadRequest() throws Exception {
    when(authService.validateUserRequestAndReturnUser(anyLong()))
        .thenThrow(IllegalArgumentException.class);

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            post("/api/v1/users/1/mentors/2")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isBadRequest());
  }

  @Test
  void saveMentor_withIncorrectMentorId_isBadRequest() throws Exception {
    when(authService.validateUserRequestAndReturnUser(anyLong())).thenReturn(new User());
    when(userService.saveMentor(any(), anyLong())).thenThrow(IllegalArgumentException.class);

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            post("/api/v1/users/1/mentors/2")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deleteMentor_withIncorrectUserId_isBadRequest() throws Exception {
    when(authService.validateUserRequestAndReturnUser(anyLong()))
        .thenThrow(IllegalArgumentException.class);

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            delete("/api/v1/users/1/mentors/2/saved")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isBadRequest());
  }

  @Test
  void deleteMentor_withCorrectCredentials_isOk() throws Exception {
    when(authService.validateUserRequestAndReturnUser(anyLong())).thenReturn(new User());
    doNothing().when(userService).deleteMentor(any(), anyLong());

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            delete("/api/v1/users/1/mentors/2/saved")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isOk());
  }

  @Test
  void completeEnrolledModuleItem_status_ok() throws Exception {
    doNothing().when(userService).completeEnrolledModuleItem(anyLong());
    String jwt = generateJwtToken();
    mockMvc
        .perform(
            patch("/api/v1/users/1/enrolledModuleItems/1")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
         .andExpect(status().isNoContent());
   }


  private String generateJwtToken() {
    User user = new User("Test", "pass");
    user.setRole(new Role("STUDENT"));
    return "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
  }
}
