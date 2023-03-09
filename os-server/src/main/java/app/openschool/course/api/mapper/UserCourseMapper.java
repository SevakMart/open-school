package app.openschool.course.api.mapper;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.dto.UserCourseDto;
import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.Module;
import app.openschool.course.module.item.ModuleItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserCourseMapper {

  public static List<UserCourseDto> toUserCourseDtoList(List<EnrolledCourse> enrolledCourseList) {
    List<UserCourseDto> userEnrolledCourseDtoList = new ArrayList<>();
    for (EnrolledCourse enrolledCourse : enrolledCourseList) {
      userEnrolledCourseDtoList.add(toUserCourseDto(enrolledCourse));
    }
    return userEnrolledCourseDtoList;
  }

  public static UserCourseDto toUserCourseDto(EnrolledCourse enrolledCourse) {
    if (!enrolledCourse.getCourseStatus().isInProgress()) {
      return new UserCourseDto(
          enrolledCourse.getId(),
          enrolledCourse.getCourse().getTitle(),
          enrolledCourse.getCourseStatus().getType(),
          100);
    }
    Long id = enrolledCourse.getCourse().getId();
    long courseRemainingTime = getCourseReamingTime(enrolledCourse);
    long courseTotalEstimatedTime = getCourseTotalEstimatedTime(enrolledCourse.getCourse());
    long percentage = 100L - ((courseRemainingTime * 100L) / courseTotalEstimatedTime);
    return new UserCourseDto(
        enrolledCourse.getId(),
        enrolledCourse.getCourse().getTitle(),
        enrolledCourse.getCourseStatus().getType(),
        percentage,
        courseRemainingTime,
        enrolledCourse.getDueDate(),
        id);
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
