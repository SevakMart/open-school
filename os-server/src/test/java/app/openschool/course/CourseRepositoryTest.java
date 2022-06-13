package app.openschool.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.course.module.Module;
import app.openschool.course.module.ModuleRepository;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.ModuleItemRepository;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.company.Company;
import app.openschool.user.company.CompanyRepository;
import app.openschool.user.role.RoleRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class CourseRepositoryTest {
  @Autowired CategoryRepository categoryRepository;
  @Autowired DifficultyRepository difficultyRepository;
  @Autowired LanguageRepository languageRepository;
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
    User mentor = new User();
    mentor.setId(1L);
    mentor.setName("John");
    mentor.setSurname("Smith");
    mentor.setEmail("mmm@gmail.com");
    mentor.setPassword("pass");
    mentor.setRole(roleRepository.getById(2));
    Company company1 = new Company();
    company1.setCompanyName("BBB");
    companyRepository.save(company1);
    mentor.setCompany(companyRepository.getById(1));
    userRepository.save(mentor);

    for (long i = 1L; i < 7L; i++) {
      Course course = new Course();
      course.setId(i);
      course.setTitle("Stream");
      course.setRating(5.5);
      course.setCategory(categoryRepository.getById(1L));
      course.setDifficulty(difficultyRepository.getById(1));
      course.setLanguage(languageRepository.getById(1));
      course.setMentor(mentor);
      courseRepository.save(course);
    }

    Set<Module> moduleSet = new HashSet<>();
    for (long i = 1L; i < 5L; i++) {
      Module module = new Module();
      module.setId(i);
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
    user.setId(2L);
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
    userRepository.save(user);
  }

  @Test
  public void getSuggestedCourses() {
    User user = userRepository.getById(2L);
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
  public void searchCourses() {
    List<Course> searchedCourses =
        courseRepository
            .findAll("str", List.of(1L), List.of(1L), List.of(1L), PageRequest.of(0, 2))
            .toList();
    for (Course searchedCourse : searchedCourses) {
      assertTrue((searchedCourse.getTitle().toLowerCase()).contains("str"));
      assertEquals(1, searchedCourse.getLanguage().getId());
      assertEquals(1, searchedCourse.getDifficulty().getId());
    }
  }

  @Test
  public void findCoursesByMentorId() {
    Page<Course> coursePage = courseRepository.findCoursesByMentorId(1L, PageRequest.of(0, 3));
    assertEquals(3, coursePage.getSize());
  }
}
