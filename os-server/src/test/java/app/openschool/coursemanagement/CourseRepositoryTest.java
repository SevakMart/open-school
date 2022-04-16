package app.openschool.coursemanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.course.module.Module;
import app.openschool.course.module.ModuleRepository;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.ModuleItemRepository;
import app.openschool.course.module.item.status.ModuleItemStatusRepository;
import app.openschool.course.module.status.ModuleStatusRepository;
import app.openschool.course.status.CourseStatus;
import app.openschool.course.status.CourseStatusRepository;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.company.Company;
import app.openschool.user.company.CompanyRepository;
import app.openschool.user.role.RoleRepository;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class CourseRepositoryTest {
  @Autowired CategoryRepository categoryRepository;
  @Autowired DifficultyRepository difficultyRepository;
  @Autowired LanguageRepository languageRepository;
  @Autowired CourseStatusRepository courseStatusRepository;
  @Autowired ModuleStatusRepository moduleStatusRepository;
  @Autowired ModuleItemStatusRepository moduleItemStatusRepository;
  @Autowired ModuleRepository moduleRepository;
  @Autowired ModuleItemRepository moduleItemRepository;
  @Autowired CourseRepository courseRepository;
  @Autowired CompanyRepository companyRepository;
  @Autowired RoleRepository roleRepository;
  @Autowired UserRepository userRepository;

  @BeforeEach
  public void setup() {
    Difficulty difficulty = new Difficulty();
    difficulty.setTitle("Medium");
    difficultyRepository.save(difficulty);
    Language language = new Language();
    language.setTitle("English");
    languageRepository.save(language);
    Category category = new Category();
    category.setTitle("Java");
    categoryRepository.save(category);

    for (long i = 1L; i < 7L; i++) {
      Course course = new Course();
      course.setId(i);
      course.setTitle("Stream");
      course.setRating(5.5);
      course.setCategory(categoryRepository.getById(1L));
      course.setDifficulty(difficultyRepository.getById(1));
      course.setLanguage(languageRepository.getById(1));
      course.setCourseStatus(
          i < 3 ? courseStatusRepository.getById(i) : courseStatusRepository.getById(1L));
      courseRepository.save(course);
    }

    Set<Module> moduleSet = new HashSet<>();
    for (long i = 1L; i < 5L; i++) {
      Module module = new Module();
      module.setId(i);
      module.setModuleStatus(
          i < 3 ? moduleStatusRepository.getById(i) : moduleStatusRepository.getById(1L));
      module.setCourse(courseRepository.getById(1L));
      moduleSet.add(module);
      moduleRepository.save(module);
    }
    courseRepository.getById(1L).setModules(moduleSet);

    Set<ModuleItem> moduleItemsModule1 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      ModuleItem moduleItem = new ModuleItem();
      moduleItem.setId(i);
      moduleItem.setModuleItemType("video");
      moduleItem.setEstimatedTime(35L);
      moduleItem.setGrade(100);
      moduleItem.setModuleItemStatus(moduleItemStatusRepository.getById(1L));
      moduleItem.setModule(moduleRepository.getById(1L));
      moduleItemsModule1.add(moduleItem);
      moduleItemRepository.save(moduleItem);
    }

    Module module1 =
        courseRepository.getById(1L).getModules().stream()
            .filter(module -> module.getId().equals(1L))
            .findFirst()
            .get();
    module1.setModuleItems(moduleItemsModule1);
    moduleRepository.save(module1);

    Set<ModuleItem> moduleItemsModule2 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      ModuleItem moduleItem = new ModuleItem();
      moduleItem.setId(i + 2);
      moduleItem.setModuleItemType("reading");
      moduleItem.setEstimatedTime(25L);
      moduleItem.setModuleItemStatus(
          i < 2 ? moduleItemStatusRepository.getById(i) : moduleItemStatusRepository.getById(2L));
      moduleItem.setModule(moduleRepository.getById(2L));
      moduleItemsModule2.add(moduleItem);
      moduleItemRepository.save(moduleItem);
    }

    Module module2 =
        courseRepository.getById(1L).getModules().stream()
            .filter(module -> module.getId().equals(2L))
            .findFirst()
            .get();
    module2.setModuleItems(moduleItemsModule2);
    moduleRepository.save(module2);

    User user = new User();
    user.setId(1L);
    user.setName("John");
    user.setSurname("Smith");
    user.setEmail("aaa@gmail.com");
    user.setPassword("pass");
    user.setRole(roleRepository.getById(3));
    Company company = new Company();
    company.setCompanyName("AAA");
    companyRepository.save(company);
    user.setCompany(companyRepository.getById(1));
    Set<Category> categorySet = new HashSet<>();
    categorySet.add(categoryRepository.getById(1L));
    user.setCategories(categorySet);
    user.setCourses(new HashSet<>(courseRepository.findAll()));
    userRepository.save(user);
  }

  @Test
  public void getSuggestedCourses() {
    User user = userRepository.getById(1L);
    List<Course> courseList = courseRepository.getSuggestedCourses(user.getId());
    assertEquals(
        user.getCategories().stream().findFirst().get().getTitle(),
        courseList.get(0).getCategory().getTitle());
    assertEquals(4, courseList.size());
  }

  @Test
  public void getRandomSuggestedCourses() {
    List<Course> courseList = courseRepository.getRandomSuggestedCourses(4);
    assertEquals(4, courseList.size());
  }

  @Test
  public void findAllUserCourses() {
    List<Course> allUserCourses = courseRepository.findAllUserCourses(1L);

    assertEquals(6, allUserCourses.size());
    assertEquals(
        5,
        allUserCourses.stream().filter(course -> course.getCourseStatus().isInProgress()).count());
    assertEquals(
        4,
        allUserCourses.stream()
            .filter(course -> course.getId().equals(1L))
            .map(Course::getModules)
            .mapToLong(Collection::size)
            .sum());
    assertEquals(
        4,
        allUserCourses.stream()
            .filter(course -> course.getId().equals(1L))
            .map(Course::getModules)
            .flatMap(Collection::stream)
            .filter(module -> module.getId().equals(1L) || module.getId().equals(2L))
            .map(Module::getModuleItems)
            .mapToLong(Collection::size)
            .sum());
  }

  @Test
  public void findUserCoursesByStatus() {
    List<Course> coursesInProgress =
        courseRepository.findUserCoursesByStatus(1L, courseStatusRepository.getById(1L).getId());

    assertEquals(5, coursesInProgress.size());
    List<CourseStatus> statusList =
        coursesInProgress.stream().map(Course::getCourseStatus).collect(Collectors.toList());
    for (CourseStatus status : statusList) {
      assertTrue("COURSE STATUS ISN'T IN PROGRESS", status.isInProgress());
    }

    List<Course> coursesCompeted =
        courseRepository.findUserCoursesByStatus(1L, courseStatusRepository.getById(2L).getId());

    assertEquals(1, coursesCompeted.size());
    List<CourseStatus> statusListCompleted =
        coursesCompeted.stream().map(Course::getCourseStatus).collect(Collectors.toList());
    for (CourseStatus status : statusListCompleted) {
      assertTrue("COURSE STATUS ISN'T COMPLETED", !status.isInProgress());
    }
  }
}
