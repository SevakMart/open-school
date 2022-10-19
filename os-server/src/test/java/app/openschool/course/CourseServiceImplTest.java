package app.openschool.course;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.course.api.dto.CreateCourseRequest;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.keyword.Keyword;
import app.openschool.course.keyword.KeywordRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.course.module.Module;
import app.openschool.course.module.api.dto.CreateModuleRequest;
import app.openschool.course.module.api.mapper.ModuleMapper;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.api.dto.CreateModuleItemRequest;
import app.openschool.course.module.item.type.ModuleItemType;
import app.openschool.course.module.item.type.ModuleItemTypeRepository;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.role.Role;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;



@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

  @Mock private CourseRepository courseRepository;
  @Mock private CategoryRepository categoryRepository;
  @Mock private DifficultyRepository difficultyRepository;
  @Mock private LanguageRepository languageRepository;
  @Mock private KeywordRepository keywordRepository;
  @Mock private UserRepository userRepository;
  @Mock private ModuleItemTypeRepository moduleItemTypeRepository;


  //  @InjectMocks
  private CourseServiceImpl courseService;

  @BeforeEach
  void setUp() {
    courseService =
        new CourseServiceImpl(
            courseRepository,
            categoryRepository,
            difficultyRepository,
            languageRepository,
            keywordRepository,
            userRepository,
            moduleItemTypeRepository);
  }

  @Test
  public void findCourseByNonexistentId() {
    long wrongId = 999L;
    when(courseRepository.findById(wrongId)).thenReturn(Optional.empty());
    assertEquals(Optional.empty(), courseService.findCourseById(wrongId));
  }

  @Test
  public void add_withCorrectArguments_returnsCreatedCourse() {

    // given
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getName())
        .thenReturn("Test@gmail.com");
    Category parentCategory = new Category("Software engineering", "S3/images/22124", null);
    User user = new User("John", "Smith", "Test@gmail.com", "Test858#", new Role("MENTOR"));
    given(userRepository.findUserByEmail(anyString())).willReturn(user);
    var keywords = createKeywords();
    Category subCategory = new Category("Java", "S3/images/686451", parentCategory);
    Course expectedCourse = new Course();
    expectedCourse.setKeywords(keywords);
    expectedCourse.setTitle("Spring");
    String courseDescription =
        "Through this course, get a theoretical overview of the Spring framework";
    Difficulty difficulty = new Difficulty("Advanced");
    Language language = new Language("English");
    expectedCourse.setDescription(courseDescription);
    expectedCourse.setGoal("Improving skills");
    expectedCourse.setRating(0.0);
    expectedCourse.setCategory(subCategory);
    expectedCourse.setDifficulty(difficulty);
    expectedCourse.setLanguage(language);
    expectedCourse.setMentor(user);
    Module module1 = new Module();
    module1.setTitle("Stream");
    String module1Description = "Stream API is used to process collections of objects";
    module1.setDescription(module1Description);
    module1.setCourse(expectedCourse);
    ModuleItemType moduleItemType1 = new ModuleItemType();
    moduleItemType1.setType("Video");
    ModuleItem moduleItem1 = new ModuleItem();
    moduleItem1.setTitle("Functional Interfaces");
    moduleItem1.setEstimatedTime(370L);
    moduleItem1.setLink("https://861");
    moduleItem1.setModuleItemType(moduleItemType1);
    moduleItem1.setModule(module1);
    module1.setModuleItems(Set.of(moduleItem1));
    Module module2 = new Module();
    module2.setTitle("Collections");
    String module2Description =
        "The Collection in Java is a framework that provides an architecture to store "
            + "and manipulate the group of objects";
    module2.setDescription(module2Description);
    module2.setCourse(expectedCourse);
    ModuleItem moduleItem2 = new ModuleItem();
    moduleItem2.setTitle("List");
    moduleItem2.setEstimatedTime(250L);
    moduleItem2.setLink("https://654");
    moduleItem2.setModuleItemType(moduleItemType1);
    moduleItem2.setModule(module2);
    ModuleItem moduleItem3 = new ModuleItem();
    moduleItem3.setTitle("Set");
    moduleItem3.setEstimatedTime(185L);
    moduleItem3.setLink("https://874");
    ModuleItemType moduleItemType2 = new ModuleItemType();
    moduleItemType1.setType("Reading");
    moduleItem3.setModuleItemType(moduleItemType2);
    moduleItem3.setModule(module2);
    module2.setModuleItems(Set.of(moduleItem2, moduleItem3));
    Set<Module> modules = Set.of(module1, module2);
    expectedCourse.setModules(modules);
    given(courseRepository.save(any())).willReturn(expectedCourse);
    CreateModuleItemRequest createModuleItemRequest1 =
        new CreateModuleItemRequest(1L, "Stream", 1L, "https://861", 370L);
    CreateModuleRequest createModuleRequest1 =
        new CreateModuleRequest("Stream", module1Description, Set.of(createModuleItemRequest1));
    CreateModuleItemRequest createModuleItemRequest2 =
        new CreateModuleItemRequest(2L, "List", 1L, "https://654", 250L);
    CreateModuleItemRequest createModuleItemRequest3 =
        new CreateModuleItemRequest(2L, "Set", 2L, "https://874", 185L);
    CreateModuleRequest createModuleRequest2 =
        new CreateModuleRequest(
            "Collections",
            module2Description,
            Set.of(createModuleItemRequest2, createModuleItemRequest3));
    CreateCourseRequest createCourseRequest =
        new CreateCourseRequest(
            "Spring",
            courseDescription,
            "Improving skills",
            1L,
            3,
            1,
            Set.of(1L, 2L),
            Set.of(createModuleRequest1, createModuleRequest2));

    // when
    Course actualCourse = courseService.add(createCourseRequest);

    // then
    Set<ModuleItem> moduleItems =
        actualCourse.getModules().stream()
            .map(Module::getModuleItems)
            .flatMap(Collection::stream)
            .collect(Collectors.toSet());
    assertEquals(expectedCourse.getTitle(), actualCourse.getTitle());
    assertEquals(actualCourse.getModules().size(), 2);
    assertEquals(moduleItems.size(), 3);
    verify(courseRepository, times(1)).save(any());
  }

  @Test
  public void add_withIncorrectCategoryId_throwsIllegalArgumentException() {
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getName())
        .thenReturn("Test@gmail.com");
    User user = new User("John", "Smith", "Test@gmail.com", "Test858#", new Role("MENTOR"));
    given(userRepository.findUserByEmail(anyString())).willReturn(user);

    Category parentCategory = new Category("Software engineering", "S3/images/22124", null);
    Category subCategory = new Category("Java", "S3/images/686451", parentCategory);
    Difficulty difficulty = new Difficulty("Advanced");
    Language language = new Language("English");
    var keywords = createKeywords();
    Course expectedCourse = new Course();
    expectedCourse.setKeywords(keywords);
    expectedCourse.setTitle("Spring");
    String courseDescription =
        "Through this course, get a theoretical overview of the Spring framework";
    expectedCourse.setDescription(courseDescription);
    expectedCourse.setGoal("Improving skills");
    expectedCourse.setRating(0.0);
    expectedCourse.setCategory(subCategory);
    expectedCourse.setDifficulty(difficulty);
    expectedCourse.setLanguage(language);
    expectedCourse.setMentor(user);
    Set<CreateModuleRequest> createCourseRequests = new HashSet<>();
    createCourseRequests.add(createModuleRequest());
    ModuleItem moduleItem = new ModuleItem();
    moduleItem.setId(1L);
    moduleItem.setTitle("java");
    ModuleItemType moduleItemType = new ModuleItemType();
    moduleItemType.setType("java");
    moduleItem.setModuleItemType(moduleItemType);
    Set<ModuleItem> moduleItems = new HashSet<>();
    moduleItems.add(moduleItem);
    Course course = new Course(1L, "any");
    when(courseRepository.save(any())).thenReturn(course);
    Module module = new Module(1L, expectedCourse, moduleItems);
    Set<Module> modules = new HashSet<>();
    modules.add(module);
    try (MockedStatic<ModuleMapper> utilities = Mockito.mockStatic(ModuleMapper.class)) {
      utilities.when(() -> ModuleMapper.toModules(any(), any())).thenReturn(modules);
    }
    assertDoesNotThrow(() -> courseService.add(createCourseRequest()));
  }

  @Test
  public void add_withIncorrectDifficultyId_throwsIllegalArgumentException() {
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getName())
        .thenReturn("Test@gmail.com");
    User user = new User("John", "Smith", "Test@gmail.com", "Test858#", new Role("MENTOR"));
    given(userRepository.findUserByEmail(anyString())).willReturn(user);
    given(courseRepository.save(any())).willThrow(new IllegalArgumentException());
    assertThatThrownBy(() -> courseService.add(createCourseRequest()))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void add_withIncorrectLanguageId_throwsIllegalArgumentException() {

    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getName())
        .thenReturn("Test@gmail.com");
    User user = new User("John", "Smith", "Test@gmail.com", "Test858#", new Role("MENTOR"));
    given(userRepository.findUserByEmail(anyString())).willReturn(user);
    assertDoesNotThrow(() -> courseService.add(createCourseRequest()));
  }

  @Test
  public void add_withIncorrectKeywordIds_throwsIllegalArgumentException() {
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getName())
        .thenReturn("Test@gmail.com");
    given(userRepository.findUserByEmail(anyString())).willReturn(new User());
    assertDoesNotThrow(() -> courseService.add(createCourseRequest()));
  }

  private Set<Keyword> createKeywords() {
    Keyword keyword1 = new Keyword("Programming");
    Keyword keyword2 = new Keyword("Java");
    return Set.of(keyword1, keyword2);
  }

  private CreateModuleRequest createModuleRequest() {
    return new CreateModuleRequest("Java", "JavaCore", moduleItemRequests());
  }

  private CreateCourseRequest createCourseRequest() {
    return new CreateCourseRequest(
        "Spring",
        "Spring course",
        "Improving skills",
        2L,
        3,
        1,
        Set.of(1L, 2L),
        Set.of(createModuleRequest()));
  }

  private Set<CreateModuleItemRequest> moduleItemRequests() {
    CreateModuleItemRequest createModuleItemRequest = new CreateModuleItemRequest();
    createModuleItemRequest.setTitle("Java");
    createModuleItemRequest.setLink("C:\\user");
    createModuleItemRequest.setModuleItemTypeId(1L);
    createModuleItemRequest.setEstimatedTime(256L);
    CreateModuleItemRequest createModuleItemRequest1 = new CreateModuleItemRequest();
    createModuleItemRequest.setTitle("C++");
    Set<CreateModuleItemRequest> createModuleItemRequests = new HashSet<>();
    createModuleItemRequests.add(createModuleItemRequest);
    createModuleItemRequests.add(createModuleItemRequest1);
    createModuleItemRequest.setLink("C:\\users");
    createModuleItemRequest.setModuleItemTypeId(2L);
    createModuleItemRequest.setEstimatedTime(256L);

    return createModuleItemRequests;
  }
}
