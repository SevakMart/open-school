package app.openschool.coursemanagement;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.coursemanagement.api.CategoryGenerator;
import app.openschool.coursemanagement.api.dto.PreferredCategoryDto;
import app.openschool.coursemanagement.api.dto.SavePreferredCategoriesRequestDto;
import app.openschool.coursemanagement.api.exceptions.CategoryNotFoundException;
import app.openschool.coursemanagement.api.exceptions.UserNotFoundException;
import app.openschool.coursemanagement.entity.Category;
import app.openschool.coursemanagement.entity.Course;
import app.openschool.coursemanagement.entity.CourseStatus;
import app.openschool.coursemanagement.entity.Difficulty;
import app.openschool.coursemanagement.entity.Keyword;
import app.openschool.coursemanagement.entity.Language;
import app.openschool.coursemanagement.entity.Module;
import app.openschool.coursemanagement.entity.ModuleItem;
import app.openschool.coursemanagement.entity.ModuleItemStatus;
import app.openschool.coursemanagement.entity.ModuleStatus;
import app.openschool.coursemanagement.repository.CategoryRepository;
import app.openschool.coursemanagement.repository.CourseRepository;
import app.openschool.coursemanagement.service.CourseService;
import app.openschool.coursemanagement.service.CourseServiceImpl;
import app.openschool.usermanagement.entity.Company;
import app.openschool.usermanagement.entity.Role;
import app.openschool.usermanagement.entity.User;
import app.openschool.usermanagement.repository.UserRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

  @Mock private CategoryRepository categoryRepository;
  @Mock private CourseRepository courseRepository;
  @Mock private UserRepository userRepository;

  private CourseService courseService;

  @BeforeEach
  void setUp() {
    courseService = new CourseServiceImpl(categoryRepository, courseRepository, userRepository);
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
  @Transactional
  void mapAllCategoriesToSubcategories() {
    List<Category> categories = new ArrayList<>();
    Category parentCategory1 = new Category("Java", null);
    parentCategory1.setId(1L);
    Category parentCategory2 = new Category("JS", null);
    parentCategory2.setId(2L);
    categories.add(parentCategory1);
    categories.add(parentCategory2);
    List<Category> subcategories1 = new ArrayList<>();
    subcategories1.add(new Category("Collections", 1L));
    subcategories1.add(new Category("Generics", 1L));
    List<Category> subcategories2 = new ArrayList<>();
    subcategories2.add(new Category("Angular-JS", 2L));
    subcategories2.add(new Category("React-JS", 2L));

    List<Category> categoriesSearchedByJs = new ArrayList<>();
    List<Category> categoriesSearchedByAng = new ArrayList<>();
    categoriesSearchedByJs.add(parentCategory2);
    categoriesSearchedByAng.add(new Category("Angular-JS", 2L));

    given(categoryRepository.findCategoriesByParentCategoryId(null)).willReturn(categories);
    given(categoryRepository.findByTitleIgnoreCaseStartingWith("Js"))
        .willReturn(categoriesSearchedByJs);
    given(categoryRepository.findByTitleIgnoreCaseStartingWith("ang"))
        .willReturn(categoriesSearchedByAng);
    given(categoryRepository.findCategoryById(2L)).willReturn(parentCategory2);
    given(categoryRepository.findCategoriesByParentCategoryId(1L)).willReturn(subcategories1);
    given(categoryRepository.findCategoriesByParentCategoryId(2L)).willReturn(subcategories2);

    Map<String, List<PreferredCategoryDto>> categoryMap1 = courseService.findCategoriesByTitle(" ");

    PreferredCategoryDto categoryDto1 = new PreferredCategoryDto(null, "Collections");
    PreferredCategoryDto categoryDto2 = new PreferredCategoryDto(null, "React-JS");

    assertThat(categoryMap1.get("Java").get(0).getTitle()).isEqualTo(categoryDto1.getTitle());
    assertThat(categoryMap1.get("JS").get(1).getTitle()).isEqualTo(categoryDto2.getTitle());

    Map<String, List<PreferredCategoryDto>> categoryMap2 =
        courseService.findCategoriesByTitle("Js");

    assertThat(categoryMap2.get("JS").get(1).getTitle()).isEqualTo(categoryDto2.getTitle());
    assertEquals(2, categoryMap2.get("JS").size());

    Map<String, List<PreferredCategoryDto>> categoryMap3 =
        courseService.findCategoriesByTitle("ang");

    System.out.println(categoryMap3);
    assertEquals(1, categoryMap3.size());
    assertEquals(1, categoryMap3.get("JS").size());
    assertThat(categoryMap3.get("JS").get(0).getTitle()).isEqualTo("Angular-JS");
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
    Assertions.assertEquals(4, courseService.getSuggestedCourses(1L).size());
    Mockito.verify(courseRepository, Mockito.times(1)).getSuggestedCourses(1L);
  }

  @Test
  void savePreferredCategoriesWithWrongUserId() {
    Long userId = 1L;
    Set<Long> categoryIdSet = new HashSet<>();
    SavePreferredCategoriesRequestDto savePreferredCategoriesDto =
        new SavePreferredCategoriesRequestDto(userId, categoryIdSet);
    when(userRepository.findUserById(userId)).thenReturn(null);

    assertThatThrownBy(() -> courseService.savePreferredCategories(savePreferredCategoriesDto))
        .isInstanceOf(UserNotFoundException.class)
        .hasMessageContaining(String.valueOf(userId));
  }

  @Test
  void savePreferredCategoriesWithWrongCategoryId() {
    Long categoryId = 1L;
    Set<Long> categoryIdSet = new HashSet<>();
    categoryIdSet.add(categoryId);
    SavePreferredCategoriesRequestDto savePreferredCategoriesDto =
        new SavePreferredCategoriesRequestDto(1L, categoryIdSet);
    when(userRepository.findUserById(any())).thenReturn(new User());
    when(categoryRepository.findCategoryById(categoryId)).thenReturn(null);

    assertThatThrownBy(() -> courseService.savePreferredCategories(savePreferredCategoriesDto))
        .isInstanceOf(CategoryNotFoundException.class)
        .hasMessageContaining(String.valueOf(categoryId));
  }

  @Test
  void findUserCourses() {
    Difficulty difficulty = new Difficulty();
    difficulty.setTitle("Medium");
    Language language = new Language();
    language.setTitle("English");
    Category category = CategoryGenerator.generateCategory();

    Course course = new Course();
    course.setId(1L);
    course.setTitle("Stream");
    course.setDescription("AAA");
    course.setRating(5.5);
    course.setDueDate(LocalDate.of(2022, 10, 7));
    course.setCategory(category);
    course.setDifficulty(difficulty);
    course.setLanguage(language);

    CourseStatus courseStatusInProgress = new CourseStatus();
    courseStatusInProgress.setId(1L);
    courseStatusInProgress.setType("IN_PROGRESS");
    course.setCourseStatus(courseStatusInProgress);

    ModuleStatus moduleStatusInProgress = new ModuleStatus();
    moduleStatusInProgress.setId(1L);
    moduleStatusInProgress.setType("IN_PROGRESS");

    ModuleStatus moduleStatusCompleted = new ModuleStatus();
    moduleStatusCompleted.setId(1L);
    moduleStatusCompleted.setType("COMPLETED");

    Set<Module> moduleSet = new HashSet<>();
    for (long i = 1L; i < 4L; i++) {
      Module module = new Module();
      module.setId(i);
      module.setModuleStatus(i < 3 ? moduleStatusInProgress : moduleStatusCompleted);
      module.setCourse(course);
      moduleSet.add(module);
    }
    course.setModules(moduleSet);

    ModuleItemStatus moduleItemStatusInProgress = new ModuleItemStatus();
    moduleItemStatusInProgress.setId(1L);
    moduleItemStatusInProgress.setType("IN_PROGRESS");

    ModuleItemStatus moduleItemStatusCompleted = new ModuleItemStatus();
    moduleItemStatusCompleted.setId(1L);
    moduleItemStatusCompleted.setType("COMPLETED");

    Module module1 =
        moduleSet.stream().filter(module -> module.getId().equals(1L)).findFirst().get();
    Set<ModuleItem> moduleItemsModule1 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      ModuleItem moduleItem = new ModuleItem();
      moduleItem.setId(i);
      moduleItem.setModuleItemType("video");
      moduleItem.setEstimatedTime(35L);
      moduleItem.setGrade(100);
      moduleItem.setModuleItemStatus(moduleItemStatusInProgress);
      moduleItem.setModule(module1);
      moduleItemsModule1.add(moduleItem);
    }
    module1.setModuleItems(moduleItemsModule1);

    Module module2 =
        moduleSet.stream().filter(module -> module.getId().equals(2L)).findFirst().get();
    Set<ModuleItem> moduleItemsModule2 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      ModuleItem moduleItem = new ModuleItem();
      moduleItem.setId(i + 2);
      moduleItem.setModuleItemType("reading");
      moduleItem.setEstimatedTime(25L);
      moduleItem.setModuleItemStatus(
          i < 2 ? moduleItemStatusInProgress : moduleItemStatusCompleted);
      moduleItem.setModule(module2);
      moduleItemsModule2.add(moduleItem);
    }
    module2.setModuleItems(moduleItemsModule2);

    Module module3 =
        moduleSet.stream().filter(module -> module.getId().equals(3L)).findFirst().get();
    ModuleItem moduleItem = new ModuleItem();
    moduleItem.setId(5L);
    moduleItem.setModuleItemType("reading");
    moduleItem.setEstimatedTime(30L);
    moduleItem.setModuleItemStatus(moduleItemStatusCompleted);
    moduleItem.setModule(module3);
    Set<ModuleItem> moduleItemSetModule3 = new HashSet<>();
    moduleItemSetModule3.add(moduleItem);
    module3.setModuleItems(moduleItemSetModule3);

    when(courseRepository.findAllUserCourses(1L)).thenReturn(List.of(course));
    courseService.findUserCourses(1L, null);
    verify(courseRepository, Mockito.times(1)).findAllUserCourses(1L);
    when(courseRepository.findUserCoursesByStatus(1L, 1L)).thenReturn(List.of(course));
    courseService.findUserCourses(1L, 1L);
    verify(courseRepository, Mockito.times(1)).findUserCoursesByStatus(1L, 1L);
  }
}
