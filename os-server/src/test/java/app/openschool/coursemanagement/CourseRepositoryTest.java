package app.openschool.coursemanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import app.openschool.coursemanagement.api.CategoryGenerator;
import app.openschool.coursemanagement.entity.Category;
import app.openschool.coursemanagement.entity.Course;
import app.openschool.coursemanagement.entity.CourseStatus;
import app.openschool.coursemanagement.entity.Difficulty;
import app.openschool.coursemanagement.entity.Language;
import app.openschool.coursemanagement.entity.Module;
import app.openschool.coursemanagement.entity.ModuleItem;
import app.openschool.coursemanagement.repository.CategoryRepository;
import app.openschool.coursemanagement.repository.CourseRepository;
import app.openschool.coursemanagement.repository.CourseStatusRepository;
import app.openschool.coursemanagement.repository.DifficultyRepository;
import app.openschool.coursemanagement.repository.LanguageRepository;
import app.openschool.coursemanagement.repository.ModuleItemRepository;
import app.openschool.coursemanagement.repository.ModuleItemStatusRepository;
import app.openschool.coursemanagement.repository.ModuleRepository;
import app.openschool.coursemanagement.repository.ModuleStatusRepository;
import app.openschool.usermanagement.entity.Company;
import app.openschool.usermanagement.entity.User;
import app.openschool.usermanagement.repository.CompanyRepository;
import app.openschool.usermanagement.repository.RoleRepository;
import app.openschool.usermanagement.repository.UserRepository;
import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.event.annotation.AfterTestClass;

@DataJpaTest
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
    difficulty.setId(1L);
    difficulty.setTitle("Medium");
    difficultyRepository.save(difficulty);
    Language language = new Language();
    language.setId(1L);
    language.setTitle("English");
    languageRepository.save(language);
    Category category = CategoryGenerator.generateCategory();
    categoryRepository.save(category);

    for (long i = 1L; i < 7L; i++) {
      Course course = new Course();
      course.setId(i);
      course.setTitle("Stream");
      course.setDescription("AAA");
      course.setRating(5.5);
      course.setDueDate(LocalDate.of(2022, 10, 7));
      course.setCategory(category);
      course.setDifficulty(difficulty);
      course.setLanguage(language);
      course.setCourseStatus(
          courseStatusRepository.getById(i).getId() < 3
              ? courseStatusRepository.getById(i)
              : courseStatusRepository.getById(1L));
      courseRepository.save(course);
    }

    Set<Module> moduleSet = new HashSet<>();
    for (long i = 1L; i < 5L; i++) {
      Module module = new Module();
      module.setId(i);
      module.setModuleStatus(
          moduleStatusRepository.getById(i).getId() < 3
              ? moduleStatusRepository.getById(i)
              : moduleStatusRepository.getById(1L));
      module.setCourse(courseRepository.getById(1L));
      moduleSet.add(module);
      moduleRepository.save(module);
    }
    courseRepository.getById(1L).setModules(moduleSet);

    Set<ModuleItem> moduleItemSetModule1 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      ModuleItem moduleItem = new ModuleItem();
      moduleItem.setId(i);
      moduleItem.setModuleItemType("video");
      moduleItem.setEstimatedTime(35L);
      moduleItem.setGrade(100);
      moduleItem.setModuleItemStatus(moduleItemStatusRepository.getById(1L));
      moduleItem.setModule(moduleRepository.getById(1L));
      moduleItemSetModule1.add(moduleItem);
      moduleItemRepository.save(moduleItem);
    }

    Module module1 =
        courseRepository.getById(1L).getModules().stream()
            .filter(module -> module.getId().equals(1L))
            .findFirst()
            .get();
    module1.setModuleItems(moduleItemSetModule1);
    moduleRepository.save(module1);

    Set<ModuleItem> moduleItemSetModule2 = new HashSet<>();
    for (long i = 1L; i < 3L; i++) {
      ModuleItem moduleItem = new ModuleItem();
      moduleItem.setId(i + 2);
      moduleItem.setModuleItemType("reading");
      moduleItem.setEstimatedTime(25L);
      moduleItem.setModuleItemStatus(
          moduleItemStatusRepository.getById(i).getId() < 2
              ? moduleItemStatusRepository.getById(i)
              : moduleItemStatusRepository.getById(2L));
      moduleItem.setModule(moduleRepository.getById(2L));
      moduleItemSetModule2.add(moduleItem);
      moduleItemRepository.save(moduleItem);
    }

    Module module2 =
        courseRepository.getById(1L).getModules().stream()
            .filter(module -> module.getId().equals(2L))
            .findFirst()
            .get();
    module2.setModuleItems(moduleItemSetModule2);
    moduleRepository.save(module2);

    User user = new User();
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
    user.setRole(roleRepository.getById(3));
    Company company = new Company();
    company.setCompanyName("AAA");
    company.setId(1);
    user.setCompany(company);
    Set<Category> categorySet = new HashSet<>();
    categorySet.add(CategoryGenerator.generateCategory());
    user.setCategories(categorySet);
    user.setCourses(new HashSet<>(courseRepository.findAll()));
    companyRepository.save(company);
    userRepository.save(user);
  }

  @AfterTestClass
  public void tearDown() {
    categoryRepository.deleteAll();
    difficultyRepository.deleteAll();
    languageRepository.deleteAll();
    courseStatusRepository.deleteAll();
    moduleStatusRepository.deleteAll();
    moduleItemStatusRepository.deleteAll();
    courseRepository.deleteAll();
    companyRepository.deleteAll();
    roleRepository.deleteAll();
    userRepository.deleteAll();
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
        coursesInProgress.stream()
            .map(Course::getCourseStatus)
            .collect(Collectors.toList());
    for (CourseStatus status : statusList) {
      assertTrue("COURSE STATUS ISN'T IN PROGRESS", status.isInProgress());
    }

    List<Course> coursesCompeted =
        courseRepository.findUserCoursesByStatus(1L, courseStatusRepository.getById(2L).getId());

    assertEquals(1, coursesCompeted.size());
    List<CourseStatus> statusListCompleted =
        coursesCompeted.stream()
            .map(Course::getCourseStatus)
            .collect(Collectors.toList());
    for (CourseStatus status : statusListCompleted) {
      assertTrue("COURSE STATUS ISN'T COMPLETED", !status.isInProgress());
    }
  }
}
