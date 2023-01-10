package app.openschool.course.api.mapper;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.dto.EnrolledCourseOverviewDto;
import app.openschool.course.module.Module;
import app.openschool.course.module.api.mapper.EnrolledModuleMapper;
import app.openschool.course.module.item.ModuleItem;
import java.util.Collection;

public class EnrolledCourseMapper {

  public static EnrolledCourseOverviewDto toEnrolledCourseOverviewDto(
      EnrolledCourse enrolledCourse) {
    return new EnrolledCourseOverviewDto(
        enrolledCourse.getCourse().getTitle(),
        enrolledCourse.getCourseStatus().getType(),
        getCourseEstimatedTime(enrolledCourse.getCourse()),
        EnrolledModuleMapper.toEnrolledModuleOverviewDtoList(enrolledCourse.getEnrolledModules()));
  }

  private static long getCourseEstimatedTime(Course course) {
    return course.getModules().stream()
        .map(Module::getModuleItems)
        .flatMap(Collection::stream)
        .mapToLong(ModuleItem::getEstimatedTime)
        .sum();
  }
}
