package app.openschool.course.api.mapper;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.dto.EnrolledCourseOverviewDto;
import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.Module;
import app.openschool.course.module.item.EnrolledModuleItem;
import app.openschool.course.module.item.ModuleItem;
import java.util.Collection;
import java.util.List;

public class EnrolledCourseMapper {
  public static EnrolledCourseOverviewDto toEnrolledCourseOverviewDto(
      EnrolledCourse enrolledCourse) {

    return new EnrolledCourseOverviewDto(
        enrolledCourse.getCourse().getTitle(),
        enrolledCourse.getCourseStatus().getType(),
        getCourseGrade(enrolledCourse),
        getCourseEstimatedTime(enrolledCourse.getCourse()),
        List.of());
  }

  private static int getCourseGrade(EnrolledCourse enrolledCourse) {
    return enrolledCourse.getEnrolledModules().stream()
        .map(EnrolledModule::getEnrolledModuleItems)
        .flatMap(Collection::stream)
        .mapToInt(EnrolledModuleItem::getGrade)
        .sum();
  }

  private static long getCourseEstimatedTime(Course course) {
    return course.getModules().stream()
        .map(Module::getModuleItems)
        .flatMap(Collection::stream)
        .mapToLong(ModuleItem::getEstimatedTime)
        .sum();
  }
}
