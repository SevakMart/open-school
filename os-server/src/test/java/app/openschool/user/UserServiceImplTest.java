package app.openschool.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.category.api.CategoryGenerator;
import app.openschool.category.api.exception.CategoryNotFoundException;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.CourseGenerator;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.keyword.Keyword;
import app.openschool.course.language.Language;
import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.Module;
import app.openschool.course.module.item.EnrolledModuleItem;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.status.ModuleItemStatus;
import app.openschool.course.module.item.type.ModuleItemType;
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
  @Mock private EnrolledCourseRepository enrolledCourseRepository;

  private UserService userService;

  @BeforeEach
  void setUp() {
    userService =
        new UserServiceImpl(
            userRepository, categoryRepository, courseRepository, enrolledCourseRepository);
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
    assertEquals(3, userService.findAllMentors(pageable).getTotalPages());
    assertEquals(5, userService.findAllMentors(pageable).getTotalElements());
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
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    when(courseRepository.getSuggestedCourses(1L)).thenReturn(courseList);
    assertEquals(4, userService.getSuggestedCourses(1L).size());
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
      ModuleItemType moduleItemType = new ModuleItemType();
      moduleItemType.setType("reading");
      moduleItem.setModuleItemType(moduleItemType);
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
      ModuleItemType moduleItemType = new ModuleItemType();
      moduleItemType.setType("reading");
      moduleItem.setModuleItemType(moduleItemType);
      moduleItem.setEstimatedTime(25L);
      moduleItem.setModule(module2);
      moduleItemsModule2.add(moduleItem);
    }
    module2.setModuleItems(moduleItemsModule2);

    Module module3 =
        moduleSet.stream().filter(module -> module.getId().equals(3L)).findFirst().get();
    ModuleItem moduleItem = new ModuleItem();
    moduleItem.setModule(module3);
    moduleItem.setId(5L);
    ModuleItemType moduleItemType = new ModuleItemType();
    moduleItemType.setType("reading");
    moduleItem.setModuleItemType(moduleItemType);
    moduleItem.setEstimatedTime(30L);
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
    Set<Category> categorySet = new HashSet<>();
    categorySet.add(CategoryGenerator.generateCategory());
    user.setCategories(categorySet);
    Set<EnrolledCourse> enrolledCourses = new HashSet<>();
    enrolledCourses.add(enrolledCourse);
    user.setEnrolledCourses(enrolledCourses);
    when(userRepository.findById(1L)).thenReturn(Optional.of(user));
    assertEquals(1, userService.findEnrolledCourses(1L, null).size());
    userService.findEnrolledCourses(1L, 1L);
    assertEquals(1, userService.findEnrolledCourses(1L, 1L).size());
  }

  @Test
  void findMentorCourses() {
    final User user = UserGenerator.generateUser();
    List<Course> courseList = new ArrayList<>();
    Set<Keyword> keywordSet = new HashSet<>();
    keywordSet.add(new Keyword("softwareEngineer"));
    Difficulty difficulty = new Difficulty("medium");
    Language language = new Language("language");
    Category category = CategoryGenerator.generateCategory();
    for (long i = 0; i < 5L; i++) {
      Course course = new Course();
      course.setId(i);
      course.setTitle("title");
      course.setDescription("description");
      course.setRating(5.5);
      course.setKeywords(keywordSet);
      course.setCategory(category);
      course.setDifficulty(difficulty);
      course.setLanguage(language);
      courseList.add(course);
    }

    Page<Course> coursePage = new PageImpl<>(courseList);
    Pageable pageable = PageRequest.of(0, 6);
    when(userRepository.findById(any())).thenReturn(Optional.of(user));
    when(courseRepository.findCoursesByMentorId(user.getId(), pageable)).thenReturn(coursePage);
    assertEquals(
        5, userService.findMentorCourses(user.getId(), PageRequest.of(0, 6)).getTotalElements());
  }

  @Test
  void enrollCourse() {
    String username = "user";
    long coresId = 1L;

    when(userRepository.findByEmail(username)).thenReturn(Optional.of(new User(1L)));
    when(courseRepository.findById(coresId))
        .thenReturn(Optional.of(CourseGenerator.generateCourse()));

    userService.enrollCourse(username, coresId);
    verify(userRepository, times(1)).save(any());
  }

  @Test
  void findEnrolledCourseById_courseIsPresent_returnsEnrolledCourse() {
    when(enrolledCourseRepository.findById(anyLong()))
        .thenReturn(Optional.of(new EnrolledCourse()));
    EnrolledCourse enrolledCourse = userService.findEnrolledCourseById(1L);
    assertNotNull(enrolledCourse);
    verify(enrolledCourseRepository, times(1)).findById(anyLong());
  }

  @Test
  void findEnrolledCourseById_courseIsNotPresent_throwsException() {
    when(enrolledCourseRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThatThrownBy(() -> userService.findEnrolledCourseById(1L))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void findMentorsByName() {
    String username = "username";
    Pageable pageable = PageRequest.of(0, 2);
    Page<User> userPage = generateUserPage(pageable);
    when(userRepository.findMentorsByName(username, pageable)).thenReturn(userPage);

    assertEquals(3, userService.findMentorsByName(username, pageable).getTotalPages());
    assertEquals(5, userService.findMentorsByName(username, pageable).getTotalElements());
    verify(userRepository, Mockito.times(2)).findMentorsByName(username, pageable);
  }

  @Test
  void saveMentor_withCorrectMentorId_returnUser() {
    User mentor = new User(2L);
    User student = new User(1L);
    mentor.setRole(new Role("MENTOR"));
    when(userRepository.findUserById(2L)).thenReturn(Optional.of(mentor));

    userService.saveMentor(student, 2L);

    verify(userRepository, Mockito.times(1)).save(student);
    verify(userRepository, Mockito.times(1)).findUserById(anyLong());
  }

  @Test
  void saveMentor_withIncorrectMentorId_returnUser() {
    User student = new User(1L);
    when(userRepository.findUserById(2L)).thenReturn(Optional.empty());

    userService.saveMentor(student, 2L);

    verify(userRepository, Mockito.times(0)).save(student);
    verify(userRepository, Mockito.times(1)).findUserById(anyLong());
  }

  @Test
  void saveMentor_withFakeMentor_returnUser() {
    User fakeMentor = new User(2L);
    User student = new User(1L);
    fakeMentor.setRole(new Role("STUDENT"));
    when(userRepository.findUserById(2L)).thenReturn(Optional.of(fakeMentor));

    userService.saveMentor(student, 2L);

    verify(userRepository, Mockito.times(0)).save(student);
    verify(userRepository, Mockito.times(1)).findUserById(anyLong());
  }

  @Test
  void findSavedMentors_returnSavedMentors() {
    User user = generateUserWithSavedMentors();

    Pageable pageable = PageRequest.of(0, 2);
    Page<User> savedMentors = userService.findSavedMentors(user, pageable);

    assertEquals(5, savedMentors.getTotalElements());
    assertEquals(2, savedMentors.getPageable().getPageSize());
    verifyNoInteractions(userRepository);
  }

  @Test
  void findSavedMentorsByName_returnSavedMentors() {
    Pageable pageable = PageRequest.of(0, 2);
    when(userRepository.findSavedMentorsByName(anyLong(), anyString(), any()))
        .thenReturn(generateUserPage(pageable));

    Page<User> savedMentors = userService.findSavedMentorsByName(1L, "Mentor", pageable);

    assertEquals(5, savedMentors.getTotalElements());
    assertEquals(2, savedMentors.getPageable().getPageSize());
    verify(userRepository, times(1)).findSavedMentorsByName(anyLong(), anyString(), any());
  }

  @Test
  void deleteMentor_withExistingMentorAndContainingInUserSavedMentorsList_deleteSavedMentor() {
    User mentor = new User(2L);
    mentor.setRole(new Role("MENTOR"));
    User student = new User(1L);
    student.getMentors().add(mentor);

    when(userRepository.findUserById(2L)).thenReturn(Optional.of(mentor));

    userService.deleteMentor(student, 2L);

    assertTrue(student.getMentors().isEmpty());
    verify(userRepository, times(1)).save(student);
    verify(userRepository, times(1)).findUserById(anyLong());
  }

  @Test
  void deleteMentor_withNonexistentMentor_doNothing() {
    User student = new User(1L);
    when(userRepository.findUserById(2L)).thenReturn(Optional.empty());

    userService.deleteMentor(student, 2L);

    verify(userRepository, times(0)).save(student);
    verify(userRepository, times(1)).findUserById(anyLong());
  }

  @Test
  void deleteMentor_withExistingMentorButMissingFromUserSavedMentorsList_noUpdate() {
    User mentor = new User(2L);
    mentor.setRole(new Role("MENTOR"));
    User student = new User(1L);
    student.getMentors().add(new User(3L));

    when(userRepository.findUserById(2L)).thenReturn(Optional.of(mentor));

    userService.deleteMentor(student, 2L);

    assertEquals(1, student.getMentors().size());
    verify(userRepository, times(1)).save(student);
    verify(userRepository, times(1)).findUserById(anyLong());
  }

  private Page<User> generateUserPage(Pageable pageable) {
    List<User> userList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      userList.add(UserGenerator.generateUser());
    }
    return new PageImpl<>(userList, pageable, 5);
  }

  private User generateUserWithSavedMentors() {
    User user = new User(1L);
    Set<User> mentors = new HashSet<>();
    for (int i = 0; i < 5; i++) {
      mentors.add(UserGenerator.generateUser());
    }
    user.setMentors(mentors);
    return user;
  }
}
