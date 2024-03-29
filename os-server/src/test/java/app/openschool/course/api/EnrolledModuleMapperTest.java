package app.openschool.course.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.mapper.EnrolledModuleMapper;
import app.openschool.course.module.EnrolledModule;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EnrolledModuleMapperTest {

  @Test
  void toEnrolledModuleSet() {
    Course course = CourseGenerator.generateCourseWithEnrolledCourses();
    Set<EnrolledModule> actual =
        EnrolledModuleMapper.toEnrolledModules(course, new EnrolledCourse(1L));

    assertEquals(1, actual.size());
  }
}
