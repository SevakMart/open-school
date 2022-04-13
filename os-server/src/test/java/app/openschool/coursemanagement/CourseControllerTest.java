package app.openschool.coursemanagement;

import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.coursemanagement.api.CategoryGenerator;
import app.openschool.coursemanagement.api.dto.CategoryDto;
import app.openschool.coursemanagement.api.dto.UserCourseDto;
import app.openschool.coursemanagement.api.mapper.CategoryMapper;
import app.openschool.coursemanagement.api.mapper.CourseMapper;
import app.openschool.coursemanagement.entity.Category;
import app.openschool.coursemanagement.entity.Course;
import app.openschool.coursemanagement.entity.Difficulty;
import app.openschool.coursemanagement.entity.Keyword;
import app.openschool.coursemanagement.entity.Language;
import app.openschool.coursemanagement.service.CourseService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
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
class CourseControllerTest {

  @Autowired private MockMvc mockMvc;

  @MockBean private CourseService courseService;

  private final List<Course> courseList = new ArrayList<>();

  @Test
  @BeforeEach
  void setUp() {
    for (long i = 1L; i < 5L; i++) {
      Course course = new Course();
      course.setId(i);
      course.setTitle("Medium");
      course.setDescription("AAA");
      course.setRating(5.5);
      Difficulty difficulty = new Difficulty();
      difficulty.setId(1L);
      difficulty.setTitle("Initial");
      Language language = new Language();
      language.setId(1L);
      language.setTitle("English");
      Category category = CategoryGenerator.generateCategory();
      course.setDifficulty(difficulty);
      course.setLanguage(language);
      course.setCategory(category);
      Keyword keyword = new Keyword();
      keyword.setId(1L);
      keyword.setTitle("Programming");
      Set<Keyword> keywordSet = new HashSet<>();
      keywordSet.add(keyword);
      course.setKeywords(keywordSet);
      courseList.add(course);
    }
  }

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
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findCategoriesByTitle() throws Exception {

    when(courseService.findCategoriesByTitle(" ")).thenReturn(new HashMap<>());

    mockMvc
        .perform(get("/api/v1/category-search").queryParam("title", " "))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void getSuggestedCourses() throws Exception {

    when(courseService.getSuggestedCourses(1L))
        .thenReturn(CourseMapper.toCourseDtoList(courseList));
    mockMvc
        .perform(
            get("/api/v1/users/1/courses/suggested")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findUserCourses() throws Exception {
    List<UserCourseDto> userCourseDtoList = new ArrayList<>();
    when(courseService.findUserCourses(1L, null))
            .thenReturn(userCourseDtoList);
    mockMvc
            .perform(
                    get("/users/1/courses")
                            .contentType(APPLICATION_JSON))
            .andExpect(status().isUnauthorized());
  }
}
