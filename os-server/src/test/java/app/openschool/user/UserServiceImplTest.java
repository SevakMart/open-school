package app.openschool.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.category.api.CategoryGenerator;
import app.openschool.category.api.exception.CategoryNotFoundException;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.keyword.Keyword;
import app.openschool.course.language.Language;
import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.Module;
import app.openschool.course.module.item.EnrolledModuleItem;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.status.ModuleItemStatus;
import app.openschool.course.module.status.ModuleStatus;
import app.openschool.course.status.CourseStatus;
import app.openschool.user.api.UserGenerator;
import app.openschool.user.api.exception.UserNotFoundException;
import app.openschool.user.company.Company;
import app.openschool.user.role.Role;
import java.time.LocalDate;
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
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

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
    SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
    SecurityContextHolder.getContext()
        .setAuthentication(new TestingAuthenticationToken(user, null));
    given(userRepository.findUserByEmail(anyString())).willReturn(user);
    when(userRepository.getById(1L)).thenReturn(user);
    when(courseRepository.getSuggestedCourses(1L)).thenReturn(courseList);
    Assertions.assertEquals(4, userService.getSuggestedCourses().size());
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

  @Test
  void findEnrolledCourses() {
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
    course.setCategory(category);
    course.setDifficulty(difficulty);
    course.setLanguage(language);

    CourseStatus courseStatusInProgress = new CourseStatus();
    courseStatusInProgress.setId(1L);
    courseStatusInProgress.setType("IN_PROGRESS");

    EnrolledCourse enrolledCourse = new EnrolledCourse();
    enrolledCourse.setId(1L);
    enrolledCourse.setCourse(course);
    enrolledCourse.setCourseStatus(courseStatusInProgress);
    enrolledCourse.setDueDate(LocalDate.of(2022, 10, 7));

    Set<Module> moduleSet = new HashSet<>();
    for (long i = 1L; i < 4L; i++) {
      Module module = new Module();
      module.setId(i);
      module.setCourse(course);
      moduleSet.add(module);
    }
    course.setModules(moduleSet);

    ModuleStatus moduleStatusInProgress = new ModuleStatus();
    moduleStatusInProgress.setId(1L);
    moduleStatusInProgress.setType("IN_PROGRESS");

    ModuleStatus moduleStatusCompleted = new ModuleStatus();
    moduleStatusCompleted.setId(1L);
    moduleStatusCompleted.setType("COMPLETED");

    Set<EnrolledModule> enrolledModuleSet = new HashSet<>();
    for (long i = 1L; i < 4L; i++) {
      EnrolledModule enrolledModule = new EnrolledModule();
      enrolledModule.setId(i);
      List<Module> modules = new ArrayList<>(moduleSet);
      enrolledModule.setModule(modules.get((int) i - 1));
      enrolledModule.setModuleStatus(i < 3 ? moduleStatusInProgress : moduleStatusCompleted);
      enrolledModule.setEnrolledCourse(enrolledCourse);
      enrolledModuleSet.add(enrolledModule);
    }
    enrolledCourse.setEnrolledModules(enrolledModuleSet);

    Module module1 =
        moduleSet.stream().filter(module -> module.getId().equals(1L)).findFirst().get();
    Set<ModuleItem> moduleItemsModule1 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      ModuleItem moduleItem = new ModuleItem();
      moduleItem.setId(i);
      moduleItem.setModuleItemType("video");
      moduleItem.setEstimatedTime(35L);
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
    moduleItem.setModule(module3);
    Set<ModuleItem> moduleItemsModule3 = new HashSet<>();
    moduleItemsModule3.add(moduleItem);
    module3.setModuleItems(moduleItemsModule3);

    ModuleItemStatus moduleItemStatusInProgress = new ModuleItemStatus();
    moduleItemStatusInProgress.setId(1L);
    moduleItemStatusInProgress.setType("IN_PROGRESS");

    ModuleItemStatus moduleItemStatusCompleted = new ModuleItemStatus();
    moduleItemStatusCompleted.setId(1L);
    moduleItemStatusCompleted.setType("COMPLETED");

    EnrolledModule enrolledModule1 =
        enrolledModuleSet.stream().filter(module -> module.getId().equals(1L)).findFirst().get();
    Set<EnrolledModuleItem> enrolledModuleItemsEnrolledModule1 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      EnrolledModuleItem enrolledModuleItem = new EnrolledModuleItem();
      enrolledModuleItem.setId(i);
      enrolledModuleItem.setGrade(100);
      List<ModuleItem> moduleItems = new ArrayList<>(moduleItemsModule1);
      enrolledModuleItem.setModuleItem(moduleItems.get((int) i - 1));
      enrolledModuleItem.setModuleItemStatus(moduleItemStatusInProgress);
      enrolledModuleItem.setEnrolledModule(enrolledModule1);
      enrolledModuleItemsEnrolledModule1.add(enrolledModuleItem);
    }
    enrolledModule1.setEnrolledModuleItems(enrolledModuleItemsEnrolledModule1);

    EnrolledModule enrolledModule2 =
        enrolledModuleSet.stream().filter(module -> module.getId().equals(2L)).findFirst().get();
    Set<EnrolledModuleItem> enrolledModuleItemsEnrolledModule2 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      EnrolledModuleItem enrolledModuleItem = new EnrolledModuleItem();
      enrolledModuleItem.setId(i + 2);
      enrolledModuleItem.setGrade(100);
      List<ModuleItem> moduleItems = new ArrayList<>(moduleItemsModule2);
      enrolledModuleItem.setModuleItem(moduleItems.get((int) i - 1));
      enrolledModuleItem.setModuleItemStatus(
          i < 2 ? moduleItemStatusInProgress : moduleItemStatusCompleted);
      enrolledModuleItem.setEnrolledModule(enrolledModule2);
      enrolledModuleItemsEnrolledModule2.add(enrolledModuleItem);
    }
    enrolledModule2.setEnrolledModuleItems(enrolledModuleItemsEnrolledModule2);

    EnrolledModule enrolledModule3 =
        enrolledModuleSet.stream().filter(module -> module.getId().equals(3L)).findFirst().get();
    EnrolledModuleItem enrolledModuleItem = new EnrolledModuleItem();
    enrolledModuleItem.setId(5L);
    enrolledModuleItem.setGrade(100);
    enrolledModuleItem.setModuleItemStatus(moduleItemStatusCompleted);
    enrolledModuleItem.setModuleItem(moduleItem);
    enrolledModuleItem.setEnrolledModule(enrolledModule3);
    Set<EnrolledModuleItem> enrolledModuleItemsModule3 = new HashSet<>();
    enrolledModuleItemsModule3.add(enrolledModuleItem);
    enrolledModule3.setEnrolledModuleItems(enrolledModuleItemsModule3);
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
    SecurityContextHolder.setContext(SecurityContextHolder.createEmptyContext());
    SecurityContextHolder.getContext()
        .setAuthentication(new TestingAuthenticationToken(user, null));
    given(userRepository.findUserByEmail(anyString())).willReturn(new User(1L));
    when(enrolledcourseRepository.findAllUserEnrolledCourses(1L))
        .thenReturn(List.of(enrolledCourse));
    userService.findEnrolledCourses(null);
    verify(enrolledcourseRepository, Mockito.times(1)).findAllUserEnrolledCourses(1L);
    when(enrolledcourseRepository.findUserEnrolledCoursesByStatus(1L, 1L))
        .thenReturn(List.of(enrolledCourse));
    userService.findEnrolledCourses(1L);
    verify(enrolledcourseRepository, Mockito.times(1)).findUserEnrolledCoursesByStatus(1L, 1L);
  }
}
