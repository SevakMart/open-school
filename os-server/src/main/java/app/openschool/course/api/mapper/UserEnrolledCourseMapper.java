package app.openschool.course.api.mapper;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.dto.UserEnrolledCourseDto;
import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.Module;
import app.openschool.course.module.item.ModuleItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserEnrolledCourseMapper {

  public static List<UserEnrolledCourseDto> toUserEnrolledCourseDtoList(
      List<EnrolledCourse> enrolledCourseList) {
    List<UserEnrolledCourseDto> userEnrolledCourseDtoList = new ArrayList<>();
    for (EnrolledCourse enrolledCourse : enrolledCourseList) {
      userEnrolledCourseDtoList.add(toUserEnrolledCourseDto(enrolledCourse));
    }
    return userEnrolledCourseDtoList;
  }

  public static UserEnrolledCourseDto toUserEnrolledCourseDto(EnrolledCourse enrolledCourse) {
    if (!enrolledCourse.getCourseStatus().isInProgress()) {
      return new UserEnrolledCourseDto(
          enrolledCourse.getCourse().getTitle(), enrolledCourse.getCourseStatus().getType(), 100);
    }
    long courseRemainingTime = getCourseReamingTime(enrolledCourse);
    long courseTotalEstimatedTime = getCourseTotalEstimatedTime(enrolledCourse.getCourse());
    long percentage = 100L - ((courseRemainingTime * 100L) / courseTotalEstimatedTime);
    return new UserEnrolledCourseDto(
        enrolledCourse.getCourse().getTitle(),
        enrolledCourse.getCourseStatus().getType(),
        percentage,
        courseRemainingTime,
        enrolledCourse.getDueDate());
  }

  private static long getCourseReamingTime(EnrolledCourse enrolledCourse) {
    return enrolledCourse.getEnrolledModules().stream()
        .filter(enrolledModule -> enrolledModule.getModuleStatus().isInProgress())
        .map(EnrolledModule::getEnrolledModuleItems)
        .flatMap(Collection::stream)
        .filter(enrolledModuleItem -> enrolledModuleItem.getModuleItemStatus().isInProgress())
        .mapToLong(enrolledModuleItem -> enrolledModuleItem.getModuleItem().getEstimatedTime())
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
