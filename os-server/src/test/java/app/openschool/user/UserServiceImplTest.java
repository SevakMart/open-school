package app.openschool.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.category.api.CategoryGenerator;
import app.openschool.category.api.exception.CategoryNotFoundException;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.keyword.Keyword;
import app.openschool.course.language.Language;
import app.openschool.user.api.UserGenerator;
import app.openschool.user.api.exception.UserNotFoundException;
import app.openschool.user.company.Company;
import app.openschool.user.role.Role;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
class UserServiceImplTest {

  @Mock private UserRepository userRepository;
  @Mock private CategoryRepository categoryRepository;
  @Mock private CourseRepository courseRepository;
  @Mock private EnrolledCourseRepository enrolledcourseRepository;

  private UserService userService;

  @BeforeEach
  void setUp() {
    userService =
        new UserServiceImpl(
            userRepository, categoryRepository, courseRepository, enrolledcourseRepository);
  }

  @Test
  void findAllMentors() {
    List<User> userList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      userList.add(UserGenerator.generateUser());
    }
    Pageable pageable = PageRequest.of(0, 2);
    Page<User> userPage = new PageImpl<>(userList, pageable, 5);
    when(userRepository.findAllMentors(pageable)).thenReturn(userPage);
    Assertions.assertEquals(3, userService.findAllMentors(pageable).getTotalPages());
    Assertions.assertEquals(5, userService.findAllMentors(pageable).getTotalElements());
    verify(userRepository, Mockito.times(2)).findAllMentors(pageable);
  }

  @Test
  void getSuggestedCourses() {
    final List<Course> courseList = new ArrayList<>();
    final User user = new User();
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
      difficulty.setTitle("Initial");
      Language language = new Language();
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
    when(userRepository.getById(1L)).thenReturn(user);
    when(courseRepository.getSuggestedCourses(1L)).thenReturn(courseList);
    Assertions.assertEquals(4, userService.getSuggestedCourses(1L).size());
    Mockito.verify(courseRepository, Mockito.times(1)).getSuggestedCourses(1L);
  }

  @Test
  void savePreferredCategoriesWithWrongUserId() {
    Long userId = 1L;
    Set<Long> categoryIdSet = new HashSet<>();
    when(userRepository.findUserById(userId)).thenReturn(Optional.empty());

    assertThatThrownBy(() -> userService.savePreferredCategories(userId, categoryIdSet))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessageContaining(String.valueOf(userId));
  }

  @Test
  void savePreferredCategoriesWithWrongCategoryId() {
    Long categoryId = 1L;
    Set<Long> categoryIdSet = new HashSet<>();
    categoryIdSet.add(categoryId);

    when(userRepository.findUserById(any())).thenReturn(Optional.of(new User()));
    when(categoryRepository.findCategoryById(categoryId)).thenReturn(null);

    assertThatThrownBy(() -> userService.savePreferredCategories(1L, categoryIdSet))
        .isInstanceOf(CategoryNotFoundException.class)
        .hasMessageContaining(String.valueOf(categoryId));
  }
}
