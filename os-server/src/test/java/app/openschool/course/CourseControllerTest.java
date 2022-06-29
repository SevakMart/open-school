package app.openschool.course;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.course.api.CourseGenerator;
import app.openschool.user.User;
import app.openschool.user.role.Role;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class CourseControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @MockBean private CourseServiceImpl courseService;

  @Test
  void getCourseInfoUnauthorized() throws Exception {
    mockMvc
        .perform(get("/api/v1/courses/1").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void getCourseWithWrongCourseId() throws Exception {
    when(courseService.findCourseById(1L)).thenReturn(Optional.empty());
    User user = new User("Test", "pass");
    user.setRole(new Role("STUDENT"));
    String jwt = "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
    mockMvc
        .perform(
            get("/api/v1/courses/1").contentType(APPLICATION_JSON).header("Authorization", jwt))
        .andExpect(status().isNotFound());
  }

  @Test
  void getCourseWithRightCourseId() throws Exception {
    when(courseService.findCourseById(1L))
        .thenReturn(Optional.of(CourseGenerator.generateCourseWithEnrolledCourses()));
    User user = new User("Test", "pass");
    user.setRole(new Role("STUDENT"));
    String jwt = "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
    mockMvc
        .perform(
            get("/api/v1/courses/1").contentType(APPLICATION_JSON).header("Authorization", jwt))
        .andExpect(status().isOk());
  }

  @Test
  void searchCourses() throws Exception {
    when(courseService.findAll(null, null, null, null, PageRequest.of(0, 2)))
        .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 2), 2));
    mockMvc
        .perform(
            get("/api/v1/courses/searched")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }
}
