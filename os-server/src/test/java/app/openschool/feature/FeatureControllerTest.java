package app.openschool.feature;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.feature.api.CourseSearchingFeaturesDto;
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
public class FeatureControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private FeatureService featureService;

  @Test
  void getCourseSearchingFeatures() throws Exception {
    when(featureService.getCourseSearchingFeatures())
        .thenReturn(new CourseSearchingFeaturesDto(null, null, null));
    mockMvc
        .perform(get("/api/v1/courses/features").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }
}
