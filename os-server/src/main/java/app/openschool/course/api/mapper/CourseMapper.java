package app.openschool.course.api.mapper;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.CourseInfoDto;
import app.openschool.course.api.dto.CourseInfoMentorDto;
import app.openschool.course.api.dto.CourseInfoModuleDto;
import app.openschool.course.api.dto.CourseInfoModuleItemDto;
import app.openschool.course.keyword.Keyword;
import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.status.CourseStatus;
import app.openschool.user.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class CourseMapper {

  public static List<CourseDto> toCourseDtoList(List<Course> courseList) {
    List<CourseDto> courseDtoList = new ArrayList<>();
    for (Course course : courseList) {
      courseDtoList.add(toCourseDto(course));
    }
    return courseDtoList;
  }

  public static CourseDto toCourseDto(Course course) {
    Set<String> keywords =
        course.getKeywords().stream().map(Keyword::getTitle).collect(Collectors.toSet());
    return new CourseDto(
        course.getTitle(), course.getRating(), course.getDifficulty().getTitle(), keywords);
  }

  public static CourseInfoDto toCourseInfoDto(Course course) {
    return new CourseInfoDto(
        course.getTitle(),
        course.getDescription(),
        course.getGoal(),
        course.getModules().stream()
            .map(
                (module ->
                    new CourseInfoModuleDto(
                        module.getTitle(),
                        module.getModuleItems().stream()
                            .map(
                                moduleItem ->
                                    new CourseInfoModuleItemDto(
                                        moduleItem.getModuleItemType(), moduleItem.getLink()))
                            .collect(Collectors.toSet()))))
            .collect(Collectors.toSet()),
        new CourseInfoMentorDto(
            course.getMentor().getName(),
            course.getMentor().getSurname(),
            course.getMentor().getUserImgPath(),
            course.getMentor().getLinkedinPath()),
        course.getRating(),
        course.getEnrolledCourses().size(),
        course.getDifficulty().getTitle(),
        course.getLanguage().getTitle(),
        course.getModules().stream()
            .flatMapToLong(
                module -> module.getModuleItems().stream().mapToLong(ModuleItem::getEstimatedTime))
            .sum());
  }

  public static EnrolledCourse toEnrolledCourse(Course course, User user) {

    EnrolledCourse enrolledCourse =
        new EnrolledCourse(LocalDate.now(), course, user, new CourseStatus(1L));
    Set<EnrolledModule> enrolledModules = ModuleMapper.toEnrolledModuleSet(course, enrolledCourse);
    enrolledCourse.setEnrolledModules(enrolledModules);
    return enrolledCourse;
  }
}
