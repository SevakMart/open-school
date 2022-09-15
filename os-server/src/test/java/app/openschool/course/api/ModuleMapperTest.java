package app.openschool.course.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.api.mapper.EnrolledModuleMapper;
import java.util.Set;
import org.junit.jupiter.api.Test;

class ModuleMapperTest {

  @Test
  void toEnrolledModuleSet() {
    Course course = CourseGenerator.generateCourseWithEnrolledCourses();
    Set<EnrolledModule> actual =
        EnrolledModuleMapper.toEnrolledModules(course, new EnrolledCourse(1L));

    assertEquals(1, actual.size());
  }
}
