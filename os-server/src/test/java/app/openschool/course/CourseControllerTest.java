package app.openschool.course;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;
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
class CourseControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CourseService courseService;

  @Test
  void getCourseInfo() throws Exception {

    when(courseService.findCourseById(1L)).thenReturn(Optional.empty());

    mockMvc
        .perform(get("/api/v1/courses/1").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }
}
