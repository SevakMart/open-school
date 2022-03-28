package app.openschool.coursemanagement;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseController.class)
class CourseControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CourseService courseService;

  @Test
  void findCategoriesByTitle() throws Exception {

    when(courseService.findCategoriesByTitle(" ")).thenReturn(new HashMap<>());

    mockMvc
        .perform(get("/api/v1/category-search").queryParam("title", " "))
        .andExpect(status().isForbidden());
  }
}
