package app.openschool.coursemanagement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.openschool.coursemanagement.api.CategoryGenerator;
import app.openschool.coursemanagement.entity.Category;
import app.openschool.coursemanagement.entity.Course;
import app.openschool.coursemanagement.entity.Difficulty;
import app.openschool.coursemanagement.entity.Language;
import app.openschool.coursemanagement.repository.CategoryRepository;
import app.openschool.coursemanagement.repository.CourseRepository;
import app.openschool.coursemanagement.repository.DifficultyRepository;
import app.openschool.coursemanagement.repository.LanguageRepository;
import app.openschool.usermanagement.entity.Company;
import app.openschool.usermanagement.entity.Role;
import app.openschool.usermanagement.entity.User;
import app.openschool.usermanagement.repository.CompanyRepository;
import app.openschool.usermanagement.repository.RoleRepository;
import app.openschool.usermanagement.repository.UserRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CourseRepositoryTest {
  @Autowired CategoryRepository categoryRepository;
  @Autowired UserRepository userRepository;
  @Autowired CourseRepository courseRepository;
  @Autowired DifficultyRepository difficultyRepository;
  @Autowired LanguageRepository languageRepository;
  @Autowired RoleRepository roleRepository;
  @Autowired CompanyRepository companyRepository;

  @BeforeEach
  public void setup() {
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
    Role role = new Role(1, "MENTOR");
    user.setRole(role);
    Company company = new Company();
    company.setCompanyName("AAA");
    company.setId(1);
    user.setCompany(company);
    Set<Category> categorySet = new HashSet<>();
    categorySet.add(CategoryGenerator.generateCategory());
    user.setCategories(categorySet);

    roleRepository.save(role);
    companyRepository.save(company);
    userRepository.save(user);

    for (long i = 1L; i < 7L; i++) {
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
      difficultyRepository.save(difficulty);
      languageRepository.save(language);
      categoryRepository.save(category);
      courseRepository.save(course);
    }
  }

  @Test
  public void getSuggestedCourses() {
    User user = userRepository.getById(1L);
    List<Course> courseList = courseRepository.getSuggestedCourses(user.getId());
    assertEquals(
        user.getCategories().stream().findFirst().get().getTitle(),
        (courseList.get(0).getCategory().getTitle()));
    assertEquals(4, courseList.size());
  }

  @Test
  public void getRandomSuggestedCourses() {
    List<Course> courseList = courseRepository.getRandomSuggestedCourses(4);
    assertEquals(4, courseList.size());
  }
}
