package app.openschool.course;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.course.api.CourseGenerator;
import app.openschool.course.api.dto.CourseInfoDto;
import app.openschool.course.api.dto.CreateCourseRequest;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.keyword.Keyword;
import app.openschool.course.keyword.KeywordRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.course.module.Module;
import app.openschool.course.module.api.dto.CreateModuleRequest;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.api.dto.CreateModuleItemRequest;
import app.openschool.course.module.item.type.ModuleItemType;
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
  @Mock private EnrolledCourseRepository enrolledCourseRepository;
  @Mock Authentication authentication;
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
            enrolledCourseRepository);
  }


  @Test
  void add_withCorrectArguments_returnsCreatedCourse() {

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
    Set<ModuleItem> moduleItemSet = new HashSet<>();
    String moduleDescription = "Stream API is used to process collections of objects";

    moduleItemSet.add(
        createModuleItem(
            "Functional Interfaces", 370L, "https://861", createModuleItemType("Video")));
    Module module = createModule("Stream", moduleDescription, expectedCourse, moduleItemSet);

    Set<ModuleItem> moduleItemSet1 = new HashSet<>();
    moduleItemSet1.add(
        createModuleItem("List", 250L, "https://654", createModuleItemType("Video")));
    String module2Description =
        "The Collection in Java is a framework that provides an architecture to store "
            + "and manipulate the group of objects";
    Module module2 =
        createModule("Collections", module2Description, expectedCourse, moduleItemSet1);

    ModuleItem moduleItem1 =
        createModuleItem("Set", 185L, "https://874", createModuleItemType("Reading"));
    module2.getModuleItems().add(moduleItem1);

    Set<Module> modules = Set.of(module, module2);
    expectedCourse.setModules(modules);
    given(categoryRepository.findById(any())).willReturn(Optional.of(parentCategory));
    given(difficultyRepository.findById(any())).willReturn(Optional.of(difficulty));
    given(languageRepository.findById(any())).willReturn(Optional.of(language));
    given(keywordRepository.findById(any())).willReturn(Optional.of(new Keyword(1L)));
    given(courseRepository.save(any())).willReturn(expectedCourse);
    CreateModuleItemRequest createModuleItemRequest1 =
        new CreateModuleItemRequest(1L, "Stream", 1L, "https://861", 370L);
    CreateModuleRequest createModuleRequest1 =
        new CreateModuleRequest("Stream", moduleDescription, Set.of(createModuleItemRequest1));
    CreateModuleItemRequest createModuleItemRequest2 =
        new CreateModuleItemRequest(2L, "List", 1L, "https://654", 250L);
    CreateModuleItemRequest createModuleItemRequest3 =
        new CreateModuleItemRequest(2L, "Set", 2L, "https://874", 185L);
    CreateModuleRequest createModuleRequest2 =
        new CreateModuleRequest(
            "Collections",
            module2Description,
            Set.of(createModuleItemRequest2, createModuleItemRequest3));
    CreateCourseRequest createCourseRequest = createCourseRequest();
    createCourseRequest.setCreateModuleRequests(Set.of(createModuleRequest1, createModuleRequest2));
    //        new CreateCourseRequest(
    //            "Spring",
    //            courseDescription,
    //            "Improving skills",
    //            1L,
    //            3,
    //            1,
    //            Set.of(1L, 2L),
    //            Set.of(createModuleRequest1, createModuleRequest2));

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
  void add_withIncorrectCategoryId_throwsIllegalArgumentException() {
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getName())
        .thenReturn("Test@gmail.com");
    User user = new User("John", "Smith", "Test@gmail.com", "Test858#", new Role("MENTOR"));
    given(userRepository.findUserByEmail(anyString())).willReturn(user);
    given(categoryRepository.findById(1L)).willReturn(Optional.empty());
    CreateCourseRequest course = createCourseRequest();
    assertThatThrownBy(() -> courseService.add(course))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void add_withIncorrectDifficultyId_throwsIllegalArgumentException() {
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getName())
        .thenReturn("Test@gmail.com");
    User user = new User("John", "Smith", "Test@gmail.com", "Test858#", new Role("MENTOR"));
    CreateCourseRequest course = createCourseRequest();
    given(userRepository.findUserByEmail(anyString())).willReturn(user);
    given(categoryRepository.findById(anyLong())).willReturn(Optional.of(createCategory()));
    given(difficultyRepository.findById(1)).willReturn(Optional.empty());
    assertThatThrownBy(() -> courseService.add(course))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void add_withIncorrectLanguageId_throwsIllegalArgumentException() {

    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    when(SecurityContextHolder.getContext().getAuthentication().getName())
        .thenReturn("Test@gmail.com");
    User user = new User("John", "Smith", "Test@gmail.com", "Test858#", new Role("MENTOR"));
    CreateCourseRequest courseRequest = createCourseRequest();
    given(userRepository.findUserByEmail(anyString())).willReturn(user);
    given(categoryRepository.findById(anyLong())).willReturn(Optional.of(createCategory()));
    given(difficultyRepository.findById(anyInt())).willReturn(Optional.of(createDifficulty()));

    assertThatThrownBy(() -> courseService.add(courseRequest))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void add_withIncorrectKeywordIds_throwsIllegalArgumentException() {
    Authentication authentication = mock(Authentication.class);
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    CreateCourseRequest courseRequest = createCourseRequest();
    when(SecurityContextHolder.getContext().getAuthentication().getName())
        .thenReturn("Test@gmail.com");
    given(userRepository.findUserByEmail(anyString())).willReturn(new User());
    given(categoryRepository.findById(anyLong())).willReturn(Optional.of(createCategory()));
    given(difficultyRepository.findById(anyInt())).willReturn(Optional.of(createDifficulty()));
    given(languageRepository.findById(anyInt())).willReturn(Optional.of(new Language("title")));
    given(keywordRepository.findById(1L)).willReturn(Optional.empty());
    assertThatThrownBy(() -> courseService.add(courseRequest))
        .isInstanceOf(IllegalArgumentException.class);
  }

  @Test
  void findCourseById_withCorrectCourseId_returnOptionalCourse() {
    given(courseRepository.findById(anyLong()))
        .willReturn(Optional.of(CourseGenerator.generateCourse()));
    given(enrolledCourseRepository.findByUserEmailAndCourseId(anyString(), anyLong()))
        .willReturn(Optional.of(CourseGenerator.generateEnrolledCourse()));
    setAuthenticationToMockedAuth();
    Course expectedCourse = CourseGenerator.generateCourse();
    CourseInfoDto actualCourse = courseService.findCourseById(1L);

    assertEquals(actualCourse.getTitle(), expectedCourse.getTitle());
    assertEquals(actualCourse.getDescription(), expectedCourse.getDescription());
    assertEquals(actualCourse.getGoal(), expectedCourse.getGoal());
    assertEquals(actualCourse.getLanguage(), expectedCourse.getLanguage().getTitle());
    assertEquals(actualCourse.getMentorDto().getEmailPath(), expectedCourse.getMentor().getEmail());
    assertEquals(actualCourse.getRating(), expectedCourse.getRating());
  }

  @Test
  void findCourseById_withInCorrectCourseId_returnThrowIllegalArgumentException() {

    given(courseRepository.findById(anyLong())).willReturn(Optional.empty());
    assertThatThrownBy(() -> courseService.findCourseById(1L))
        .isInstanceOf(IllegalArgumentException.class);
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
    //    return new CreateCourseRequest(
    //        "Spring",
    //        "Spring course",
    //        "Improving skills",
    //        1L,
    //        1,
    //        1,
    //        Set.of(1L),
    //        Set.of(createModuleRequest()));
    CreateCourseRequest request = new CreateCourseRequest();
    request.setTitle("Spring");
    request.setDescription("Spring course");
    request.setGoal("Improving skills");
    request.setCategoryId(1L);
    request.setDifficultyId(1);
    request.setLanguageId(1);
    request.setKeywordIds(Set.of(1L));
    request.setCreateModuleRequests(Set.of(createModuleRequest()));

    return request;
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

  private Module createModule(
      String title, String description, Course course, Set<ModuleItem> moduleItems) {
    Module module = new Module();
    module.setTitle(title);
    module.setDescription(description);
    module.setCourse(course);
    module.getModuleItems().forEach(moduleItem -> moduleItem.setModule(module));
    module.setModuleItems(moduleItems);
    return module;
  }

  private ModuleItemType createModuleItemType(String type) {
    ModuleItemType moduleItemType = new ModuleItemType();
    moduleItemType.setType(type);
    return moduleItemType;
  }

  private void setAuthenticationToMockedAuth() {
    given(authentication.getName()).willReturn("mockedEmail");
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private ModuleItem createModuleItem(
      String title, Long estimatedTime, String link, ModuleItemType moduleItemType) {
    ModuleItem moduleItem = new ModuleItem();
    moduleItem.setTitle(title);
    moduleItem.setEstimatedTime(estimatedTime);
    moduleItem.setLink(link);
    moduleItem.setModuleItemType(moduleItemType);

    return moduleItem;
  }

  private Category createCategory() {
    Category category = new Category();
    category.setId(1L);
    category.setTitle("title");
    return category;
  }

  private Difficulty createDifficulty() {
    Difficulty difficulty = new Difficulty();
    difficulty.setId(1);
    difficulty.setTitle("title");
    return difficulty;
  }
}
