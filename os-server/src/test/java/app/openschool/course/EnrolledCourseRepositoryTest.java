package app.openschool.course;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;
import static org.springframework.test.util.AssertionErrors.assertTrue;

import app.openschool.category.Category;
import app.openschool.category.CategoryRepository;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.difficulty.DifficultyRepository;
import app.openschool.course.language.Language;
import app.openschool.course.language.LanguageRepository;
import app.openschool.course.status.CourseStatus;
import app.openschool.course.status.CourseStatusRepository;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.company.Company;
import app.openschool.user.company.CompanyRepository;
import app.openschool.user.role.RoleRepository;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class EnrolledCourseRepositoryTest {

  @Autowired CategoryRepository categoryRepository;
  @Autowired DifficultyRepository difficultyRepository;
  @Autowired LanguageRepository languageRepository;
  @Autowired CourseRepository courseRepository;
  @Autowired EnrolledCourseRepository enrolledCourseRepository;
  @Autowired CompanyRepository companyRepository;
  @Autowired RoleRepository roleRepository;
  @Autowired UserRepository userRepository;
  @Autowired CourseStatusRepository courseStatusRepository;

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

    List<Course> courseList = new ArrayList<>();
    for (long i = 1L; i < 7L; i++) {
      Course course = new Course();
      course.setId(i);
      course.setTitle("Stream");
      course.setRating(5.5);
      course.setCategory(categoryRepository.getById(1L));
      course.setDifficulty(difficultyRepository.getById(1));
      course.setLanguage(languageRepository.getById(1));
      course.setMentor(mentor);
      courseList.add(course);
      courseRepository.save(course);
    }

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

    userRepository.save(user);

    Set<EnrolledCourse> enrolledCourses = new HashSet<>();
    for (long i = 1L; i < 7L; i++) {
      EnrolledCourse enrolledCourse = new EnrolledCourse();
      enrolledCourse.setId(i);
      enrolledCourse.setCourse(courseList.get((int) i - 1));
      enrolledCourse.setCourseStatus(
          i < 4 ? courseStatusRepository.getById(1L) : courseStatusRepository.getById(2L));
      enrolledCourse.setDueDate(LocalDate.of(2022, 10, 7));
      enrolledCourse.setUser(user);
      enrolledCourses.add(enrolledCourse);
      enrolledCourseRepository.save(enrolledCourse);
    }
    user.setEnrolledCourses(enrolledCourses);
  }

  @Test
  public void findAllUserEnrolledCourses() {
    List<EnrolledCourse> allUserEnrolledCourses =
        enrolledCourseRepository.findAllUserEnrolledCourses(2L);
    assertEquals(6, allUserEnrolledCourses.size());
  }

  @Test
  public void findUserEnrolledCoursesByStatus() {
    List<EnrolledCourse> coursesInProgress =
        enrolledCourseRepository.findUserEnrolledCoursesByStatus(
            2L, courseStatusRepository.getById(1L).getId());

    assertEquals(3, coursesInProgress.size());
    List<CourseStatus> statusList =
        coursesInProgress.stream()
            .map(EnrolledCourse::getCourseStatus)
            .collect(Collectors.toList());
    for (CourseStatus status : statusList) {
      assertTrue("COURSE STATUS ISN'T IN PROGRESS", status.isInProgress());
    }

    List<EnrolledCourse> coursesCompleted =
        enrolledCourseRepository.findUserEnrolledCoursesByStatus(
            2L, courseStatusRepository.getById(2L).getId());

    assertEquals(3, coursesCompleted.size());
    List<CourseStatus> statusListCompleted =
        coursesCompleted.stream().map(EnrolledCourse::getCourseStatus).collect(Collectors.toList());
    for (CourseStatus status : statusListCompleted) {
      assertTrue("COURSE STATUS ISN'T COMPLETED", !status.isInProgress());
    }
  }
}
