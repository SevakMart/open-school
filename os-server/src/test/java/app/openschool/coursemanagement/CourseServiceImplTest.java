package app.openschool.coursemanagement;

import static org.mockito.Mockito.when;

import app.openschool.coursemanagement.api.CategoryGenerator;
import app.openschool.coursemanagement.entity.Category;
import app.openschool.coursemanagement.entity.Course;
import app.openschool.coursemanagement.entity.Difficulty;
import app.openschool.coursemanagement.entity.Keyword;
import app.openschool.coursemanagement.entity.Language;
import app.openschool.coursemanagement.repository.CategoryRepository;
import app.openschool.coursemanagement.repository.CourseRepository;
import app.openschool.coursemanagement.service.CourseService;
import app.openschool.coursemanagement.service.CourseServiceImpl;
import app.openschool.usermanagement.entity.Company;
import app.openschool.usermanagement.entity.Role;
import app.openschool.usermanagement.entity.User;
import app.openschool.usermanagement.repository.UserRepository;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
public class CourseServiceImplTest {

  @Mock private CategoryRepository categoryRepository;
  @Mock private CourseRepository courseRepository;
  @Mock private UserRepository userRepository;

  private CourseService courseService;

  private final List<Course> courseList = new ArrayList<>();
  private final User user = new User();

  @BeforeEach
  void setUp() {
    courseService = new CourseServiceImpl(categoryRepository, courseRepository, userRepository);

    user.setName("John");
    user.setSurname("Smith");
    user.setProfessionName("developer");
    user.setCourseCount(3);
    user.setUserImgPath("aaa");
    user.setLinkedinPath("kkk");
    user.setEmailPath("lll");
    user.setEmail("aaa@gmail.com");
    user.setPassword("pass");
    user.setId(1L);
    Role role = new Role(1, "MENTOR");
    user.setRole(role);
    Company company = new Company();
    company.setCompanyName("AAA");
    company.setId(1);
    user.setCompany(company);
    Set<Category> categorySet = new HashSet<>();
    categorySet.add(CategoryGenerator.generateCategory());
    user.setCategories(categorySet);

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
  void findAllCategories() {
    List<Category> categoryList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      categoryList.add(CategoryGenerator.generateCategory());
    }
    Pageable pageable = PageRequest.of(0, 2);
    Page<Category> categoryPage = new PageImpl<>(categoryList, pageable, 5);
    when(categoryRepository.findAllCategories(pageable)).thenReturn(categoryPage);
    Assertions.assertEquals(3, courseService.findAllCategories(pageable).getTotalPages());
    Assertions.assertEquals(5, courseService.findAllCategories(pageable).getTotalElements());
    Mockito.verify(categoryRepository, Mockito.times(2)).findAllCategories(pageable);
  }

  @Test
  void getSuggestedCourses() {
    when(userRepository.getById(1L)).thenReturn(user);
    when(courseRepository.getSuggestedCourses(1L)).thenReturn(courseList);
    Assertions.assertEquals(4, courseService.getSuggestedCourses(1L).size());
    Mockito.verify(courseRepository, Mockito.times(1)).getSuggestedCourses(1L);
  }
}
