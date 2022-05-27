package app.openschool.course;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.course.api.dto.CourseSearchingFeaturesDto;
import java.util.List;
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

  @MockBean private CourseServiceImpl courseService;

  @Test
  void getCourseSearchingFeatures() throws Exception {
    when(courseService.getCourseSearchingFeatures()).thenReturn(new CourseSearchingFeaturesDto());
    mockMvc
        .perform(get("/api/v1/courses/features").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void searchCourses() throws Exception {
    when(courseService.searchCourses(PageRequest.of(0, 2), null, null, null, null))
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
