package app.openschool.user;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.course.Course;
import app.openschool.user.api.UserGenerator;
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
public class MentorControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private UserServiceImpl userService;

  @Test
  void findAllMentors() throws Exception {
    List<User> mentorList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      mentorList.add(UserGenerator.generateUser());
    }
    Pageable pageable = PageRequest.of(0, 2);
    Page<User> mentorPage = new PageImpl<>(mentorList, pageable, 5);
    when(userService.findAllMentors(pageable)).thenReturn(mentorPage);
    mockMvc
        .perform(
            get("/api/v1/mentors")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findMentorCourses() throws Exception {
    List<Course> mentorCourseList = new ArrayList<>();
    Page<Course> mentorCourseDtoPage = new PageImpl<>(mentorCourseList);
    when(userService.findMentorCourses(1L, null)).thenReturn(mentorCourseDtoPage);
    mockMvc
        .perform(get("/mentors/1/courses").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }
}