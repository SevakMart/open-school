package app.openschool.common;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.category.CategoryServiceImpl;
import app.openschool.category.api.CategoryGenerator;
import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.mapper.CategoryMapper;
import app.openschool.user.User;
import app.openschool.user.UserService;
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
class PublicControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CategoryServiceImpl categoryService;

  @MockBean private UserService userService;

  @Test
  void findAllCategories() throws Exception {
    List<CategoryDto> categoryDtoList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      categoryDtoList.add(CategoryMapper.toCategoryDto(CategoryGenerator.generateCategory()));
    }
    Pageable pageable = PageRequest.of(0, 2);
    Page<CategoryDto> categoryDtoPage = new PageImpl<>(categoryDtoList, pageable, 5);
    when(categoryService.findAllParentCategories(pageable)).thenReturn(categoryDtoPage);
    mockMvc
        .perform(
            get("/api/v1/public/categories")
                .queryParam("page", "1")
                .queryParam("size", "2")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

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
            get("/api/v1/public/users/mentors")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }
}
