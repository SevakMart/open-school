package app.openschool.user;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.course.api.CourseGenerator;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.UserCourseDto;
import app.openschool.user.api.UserGenerator;
import app.openschool.user.api.dto.MentorDto;
import app.openschool.user.api.mapper.MentorMapper;
import app.openschool.user.role.Role;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
class UserControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @MockBean private UserServiceImpl userService;

  @Test
  void getAllMentors() throws Exception {
    List<MentorDto> mentorDtoList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      mentorDtoList.add(MentorMapper.toMentorDto(UserGenerator.generateUser()));
    }
    Pageable pageable = PageRequest.of(0, 2);
    Page<MentorDto> mentorPage = new PageImpl<>(mentorDtoList, pageable, 5);
    when(userService.findAllMentors(pageable)).thenReturn(mentorPage);
    mockMvc
        .perform(
            get("/api/v1/users/mentors")
                .queryParam("page", "1")
                .queryParam("size", "2")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void getSuggestedCourses() throws Exception {

    List<CourseDto> courseDtoList = new ArrayList<>();

    when(userService.getSuggestedCourses(1L)).thenReturn(courseDtoList);
    mockMvc
        .perform(get("/api/v1/users/1/courses/suggested").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findEnrolledUserCourses() throws Exception {
    List<UserCourseDto> userEnrolledCourseDtoList = new ArrayList<>();
    when(userService.findUserEnrolledCourses(1L, null)).thenReturn(userEnrolledCourseDtoList);
    mockMvc
        .perform(get("/users/1/courses/enrolled").contentType(APPLICATION_JSON))
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
  void enrollCourseUnauthorizedRequest() throws Exception {
    mockMvc
        .perform(post("/api/v1/users/courses/1").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void enrollCourseWithWrongCourseId() throws Exception {
    when(userService.enrollCourse(anyString(), anyLong())).thenReturn(Optional.empty());
    User user = new User("Test", "pass");
    user.setRole(new Role("STUDENT"));
    String jwt = "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
    mockMvc
        .perform(
            post("/api/v1/users/courses/1")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isNotFound());
  }

  @Test
  void enrollCourseWithRightCourseId() throws Exception {
    when(userService.enrollCourse(anyString(), anyLong()))
        .thenReturn(Optional.of(CourseGenerator.generateCourse()));
    User user = new User("Test", "pass");
    user.setRole(new Role("STUDENT"));
    String jwt = "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
    mockMvc
        .perform(
            post("/api/v1/users/courses/1")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isCreated());
  }
}
