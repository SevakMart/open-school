package app.openschool.coursemanagement;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.coursemanagement.api.CategoryGenerator;
import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.api.mapper.CategoryMapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CourseController.class)
public class CourseControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CourseService courseService;

  @Test
  void findAllCategories() throws Exception {
    List<CategoryDto> categoryDtoList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      categoryDtoList.add(CategoryMapper.toCategoryDto(CategoryGenerator.generateCategory()));
    }
    Pageable pageable = PageRequest.of(0, 2);
    Page<CategoryDto> categoryDtoPage = new PageImpl<>(categoryDtoList, pageable, 5);
    when(courseService.findAllCategories(pageable)).thenReturn(categoryDtoPage);
    mockMvc
        .perform(
            get("/api/v1/categories")
                .queryParam("page", "1")
                .queryParam("size", "2")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
