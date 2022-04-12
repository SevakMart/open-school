package app.openschool.coursemanagement.api.mapper;

import app.openschool.coursemanagement.api.dto.UserCourseDto;
import app.openschool.coursemanagement.entity.Course;
import app.openschool.coursemanagement.entity.Module;
import app.openschool.coursemanagement.entity.ModuleItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserCourseMapper {

  public static List<UserCourseDto> toUserCourseDtoList(List<Course> courseList) {
    List<UserCourseDto> userCourseDtoList = new ArrayList<>();
    for (Course course : courseList) {
      userCourseDtoList.add(toUserCourseDto(course));
    }
    return userCourseDtoList;
  }

  public static UserCourseDto toUserCourseDto(Course course) {
    if (!course.getCourseStatus().isInProgress()) {
      return new UserCourseDto(course.getTitle(), course.getCourseStatus().getType(), 100);
    }
    long courseRemainingTime = getCourseReamingTime(course);
    long courseTotalEstimatedTime = getCourseTotalEstimatedTime(course);
    long percentage = 100L - ((courseRemainingTime * 100L) / courseTotalEstimatedTime);
    return new UserCourseDto(
        course.getTitle(),
        course.getCourseStatus().getType(),
        percentage,
        courseRemainingTime,
        course.getDueDate());
  }

  private static long getCourseReamingTime(Course course) {
    return course.getModules().stream()
        .filter(module -> module.getModuleStatus().isInProgress())
        .map(Module::getModuleItems)
        .flatMap(Collection::stream)
        .filter(moduleItem -> moduleItem.getModuleItemStatus().isInProgress())
        .mapToLong(ModuleItem::getEstimatedTime)
        .sum();
  }

  private static long getCourseTotalEstimatedTime(Course course) {
    return course.getModules().stream()
        .map(Module::getModuleItems)
        .flatMap(Collection::stream)
        .mapToLong(ModuleItem::getEstimatedTime)
        .sum();
  }
}
