package app.openschool.course.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.CourseInfoDto;
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

class CourseMapperTest {
  Course course;

  @BeforeEach
  public void setUp() {
    course = CourseGenerator.generateCourseWithEnrolledCourses();
  }

  @Test
  void toCourseDtoTest() {
    CourseDto actual = CourseMapper.toCourseDto(course);
    assertThat(actual).hasOnlyFields("id", "title", "rating", "difficulty", "keywords");
  }

  @Test
  void toCourseInfoDto() {

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    SecurityContextHolder.setContext(securityContext);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
    when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("email");


    CourseInfoDto actual =
        CourseMapper.toCourseInfoDto(CourseGenerator.generateCourseWithEnrolledCourses());
    assertThat(actual)
        .hasOnlyFields(
            "enrolledCourseId",
            "title",
            "description",
            "goal",
            "modules",
            "mentorDto",
            "rating",
            "enrolled",
            "level",
            "language",
            "duration",
            "isCurrentUserEnrolled");
  }

  @Test
  void toEnrolledCourse() {
    EnrolledCourse actual =
        CourseMapper.toEnrolledCourse(CourseGenerator.generateCourse(), new User(1L));

    assertThat(actual).isExactlyInstanceOf(EnrolledCourse.class);
  }
}
