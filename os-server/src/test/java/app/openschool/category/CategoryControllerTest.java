package app.openschool.category;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
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
  void findById_withCorrectArgument_returnsStatusOk() throws Exception {
    try (MockedStatic<CategoryMapper> mapper = Mockito.mockStatic(CategoryMapper.class)) {
      mapper.when(() -> CategoryMapper.toCategoryDto(any())).thenReturn(new CategoryDto());
    }
    given(categoryService.findById(1L)).willReturn(new Category());
    String jwt = generateJwtToken(new Role("USER"));

    mockMvc
        .perform(
            get("/api/v1/categories/1").header("Authorization", jwt).queryParam("categoryId", "1"))
        .andExpect(status().isOk());
  }

  @Test
  void findById_withIncorrectCategoryId_returnsStatusBadRequest() throws Exception {
    given(categoryService.findById(1L)).willThrow(new IllegalArgumentException());
    String jwt = generateJwtToken(new Role("USER"));
    mockMvc
        .perform(
            get("/api/v1/categories/1").header("Authorization", jwt).queryParam("categoryId", "1"))
        .andExpect(status().isBadRequest());
  }

  @Test
  void findAll_returnsStatusOk() throws Exception {
    given(categoryService.findAll()).willReturn(new ParentAndSubCategoriesDto(new HashMap<>()));
    String jwt = generateJwtToken(new Role("USER"));
    mockMvc
        .perform(get("/api/v1/categories").header("Authorization", jwt))
        .andExpect(status().isOk());
  }

  @Test
  void updateData_withCorrectArguments_returnsStatusOk() throws Exception {
    try (MockedStatic<CategoryMapper> mapper = Mockito.mockStatic(CategoryMapper.class)) {
      mapper.when(() -> CategoryMapper.toCategoryDto(any())).thenReturn(new CategoryDto());
    }
    given(categoryService.updateData(anyLong(), any(), any())).willReturn(new Category());
    String jwt = generateJwtToken(new Role("ADMIN"));
    String requestBody = "{ \"title\": \"Java\", \"parentCategoryId\": 1}";

    mockMvc
        .perform(
            patch("/api/v1/categories/1")
                .contentType(APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", jwt))
        .andExpect(status().isOk());
  }

  @Test
  void updateData_withIncorrectCategoryIdOrBlankTitleOrParentCategoryId_returnsStatusBadRequest()
      throws Exception {
    doThrow(IllegalArgumentException.class).when(categoryService).updateData(any(), any(), any());
    String jwt = generateJwtToken(new Role("ADMIN"));
    String requestBody = "{ \"title\": \" \", \"parentCategoryId\": 31}";

    mockMvc
        .perform(
            patch("/api/v1/categories/1")
                .contentType(APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", jwt))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateData_whenNewTitleLengthExceededAllowedSize_returnsStatusBadRequest() throws Exception {
    String jwt = generateJwtToken(new Role("ADMIN"));
    String requestBody =
        "{ \"title\": \" Kancsjnacjdmslmdlmvslskvmkslmvlksmsklmv"
            + "sdvslmlnnjndmlsdlms\", \"parentCategoryId\": 1}";

    mockMvc
        .perform(
            patch("/api/v1/categories/1")
                .contentType(APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", jwt))
        .andExpect(status().isBadRequest());
  }

  @Test
  void updateData_withUnsupportedRole_returnsStatusForbidden() throws Exception {
    String jwt = generateJwtToken(new Role("USER"));
    String requestBody = "{ \"title\": \"Java\", \"parentCategoryId\": 1}";

    mockMvc
        .perform(
            patch("/api/v1/categories/1")
                .contentType(APPLICATION_JSON)
                .content(requestBody)
                .header("Authorization", jwt))
        .andExpect(status().isForbidden());
  }

  @Test
  void delete_withCorrectCategoryIdAndUserWithAdminRole_returnsStatusNoContent() throws Exception {
    doNothing().when(categoryService).delete(1L, Locale.ROOT);
    String jwt = generateJwtToken(new Role("ADMIN"));

    mockMvc
        .perform(
            delete("/api/v1/categories/1")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isNoContent());
  }

  @Test
  void delete_withUnsupportedRole_returnsStatusForbidden() throws Exception {
    mockMvc
        .perform(
            delete("/api/v1/categories/1")
                .contentType(APPLICATION_JSON)
                .header("Authorization", generateJwtToken(new Role("USER"))))
        .andExpect(status().isForbidden());
  }

  @Test
  void delete_withIncorrectCategoryId_returnsStatusBadRequest() throws Exception {
    doThrow(IllegalArgumentException.class).when(categoryService).delete(any(), any());
    String jwt = generateJwtToken(new Role("ADMIN"));

    mockMvc
        .perform(
            delete("/api/v1/categories/1")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isBadRequest());
  }

  @Test
  void delete_whenDeletingCategoryIsParent_returnsStatusBadRequest() throws Exception {
    doThrow(UnsupportedOperationException.class).when(categoryService).delete(any(), any());
    String jwt = generateJwtToken(new Role("ADMIN"));

    mockMvc
        .perform(
            delete("/api/v1/categories/1")
                .contentType(APPLICATION_JSON)
                .header("Authorization", jwt))
        .andExpect(status().isBadRequest());
  }

  private String generateJwtToken(Role role) {
    User user = new User("Smith", "email", "pass", role);
    return "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
  }
}
