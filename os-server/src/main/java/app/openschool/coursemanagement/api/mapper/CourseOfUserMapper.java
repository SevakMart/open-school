package app.openschool.coursemanagement.api.mapper;

import app.openschool.coursemanagement.api.dto.CourseOfUserDto;
import app.openschool.coursemanagement.entity.Course;
import app.openschool.coursemanagement.entity.Module;
import app.openschool.coursemanagement.entity.ModuleItem;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class CourseOfUserMapper {

  public static List<CourseOfUserDto> toCourseOfUserDtoList(List<Course> courseList) {
    List<CourseOfUserDto> courseOfUserDtoList = new ArrayList<>();
    for (Course course : courseList) {
      courseOfUserDtoList.add(toCourseOfUserDto(course));
    }
    return courseOfUserDtoList;
  }

  public static CourseOfUserDto toCourseOfUserDto(Course course) {

    String courseStatus = course.getStatus().getType();
    String buttonName = courseStatus.equals("COMPLETED") ? "RATE COURSE" : "RESUME COURSE";

    if (courseStatus.equals("COMPLETED")) {
      return new CourseOfUserDto(course.getTitle(), courseStatus, 100, buttonName);
    }

    Optional<Long> remainingTimeOfCourse =
        course.getModules().stream()
            .filter(module -> module.getStatus().getType().equals("IN_PROGRESS"))
            .map(Module::getModuleItems)
            .flatMap(Collection::stream)
            .filter(moduleItem -> moduleItem.getStatus().getType().equals("IN_PROGRESS"))
            .map(ModuleItem::getEstimatedTimeImMinutes)
            .reduce(Long::sum);

    String remainingTime =
        (remainingTimeOfCourse.orElse(0L) / 60)
            + "h "
            + (remainingTimeOfCourse.orElse(0L) % 60)
            + "m";

    Long totalTimeOfCourse =
        course.getModules().stream()
            .map(Module::getModuleItems)
            .flatMap(Collection::stream)
            .map(ModuleItem::getEstimatedTimeImMinutes)
            .reduce(Long::sum)
            .orElse(1L);

    Long percentage = 100L - ((remainingTimeOfCourse.orElse(1L) * 100L) / totalTimeOfCourse);

    return new CourseOfUserDto(
        course.getTitle(),
        courseStatus,
        percentage,
        remainingTime,
        course.getDueDate(),
        buttonName);
  }
}
