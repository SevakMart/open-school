package app.openschool.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.category.api.CategoryGenerator;
import app.openschool.category.api.dto.CategoryDto;
import app.openschool.category.api.dto.ParentAndSubCategoriesDto;
import app.openschool.category.api.mapper.CategoryMapper;
import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.user.User;
import app.openschool.user.role.Role;
import java.util.ArrayList;
import java.util.HashMap;
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
public class CategoryControllerTest {
  @Autowired private MockMvc mockMvc;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @MockBean private CategoryServiceImpl categoryService;

  @Test
  void getAllCategories() throws Exception {
    List<CategoryDto> categoryDtoList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      categoryDtoList.add(CategoryMapper.toCategoryDto(CategoryGenerator.generateCategory()));
    }
    Pageable pageable = PageRequest.of(0, 2);
    Page<CategoryDto> categoryDtoPage = new PageImpl<>(categoryDtoList, pageable, 5);
    when(categoryService.findAllParentCategories(pageable)).thenReturn(categoryDtoPage);
    mockMvc
        .perform(
            get("/api/v1/categories")
                .queryParam("page", "1")
                .queryParam("size", "2")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findCategoriesByTitle() throws Exception {

    when(categoryService.findCategoriesByTitle(" ")).thenReturn(new HashMap<>());

    mockMvc
        .perform(get("/api/v1/categories/subcategories").queryParam("title", " "))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findById_withCorrectArgument_returnsStatus200() throws Exception {
    given(categoryService.findById(1L)).willReturn(new Category());
    mockStatic(CategoryMapper.class);
    given(CategoryMapper.toCategoryDto(any())).willReturn(new CategoryDto());
    String jwt = generateJwtToken();
    mockMvc
        .perform(
            get("/api/v1/categories/1").header("Authorization", jwt).queryParam("categoryId", "1"))
        .andExpect(status().isOk());
  }

  @Test
  void findById_withInCorrectCategoryId_returnsStatus400() throws Exception {
    given(categoryService.findById(1L)).willThrow(new IllegalArgumentException());
    String jwt = generateJwtToken();
    mockMvc
        .perform(
            get("/api/v1/categories/1").header("Authorization", jwt).queryParam("categoryId", "1"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void findAll_returnsStatus200() throws Exception {
    given(categoryService.findAll()).willReturn(new ParentAndSubCategoriesDto(new HashMap<>()));
    String jwt = generateJwtToken();
    mockMvc
        .perform(get("/api/v1/categories").header("Authorization", jwt))
        .andExpect(status().isOk());
  }

  private String generateJwtToken() {
    User user = new User("email", "pass");
    user.setRole(new Role("USER"));
    return "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
  }
}
