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
import app.openschool.course.module.Module;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.status.CourseStatus;
import app.openschool.user.User;
import app.openschool.user.api.mapper.MentorMapper;
import app.openschool.user.company.Company;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.context.SecurityContextHolder;

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
        course.getId(),
        course.getTitle(),
        course.getRating(),
        course.getDifficulty().getTitle(),
        keywords);
  }

  public static Page<CourseDto> toCourseDtoPage(Page<Course> coursePage) {
    List<Course> courseList = coursePage.toList();
    List<CourseDto> courseDtoList = new ArrayList<>();
    for (Course course : courseList) {
      courseDtoList.add(toCourseDto(course));
    }
    return new PageImpl<>(courseDtoList, coursePage.getPageable(), coursePage.getTotalElements());
  }

  public static CourseInfoDto toCourseInfoDto(Course course) {
    return new CourseInfoDto(
        getEnrolledCourseId(course),
        course.getTitle(),
        course.getDescription(),
        course.getGoal(),
        getCourseInfoModuleDtoSet(course),
        MentorMapper.toMentorDto(course.getMentor()),
        course.getRating(),
        course.getEnrolledCourses().size(),
        course.getDifficulty().getTitle(),
        course.getLanguage().getTitle(),
        getCourseDurationInHours(course));
  }

  public static EnrolledCourse toEnrolledCourse(Course course, User user) {
    EnrolledCourse enrolledCourse =
        new EnrolledCourse(LocalDate.now(), course, user, CourseStatus.inProgress());
    Set<EnrolledModule> enrolledModules =
        EnrolledModuleMapper.toEnrolledModules(course, enrolledCourse);
    enrolledCourse.setEnrolledModules(enrolledModules);
    return enrolledCourse;
  }

  private static Set<CourseInfoModuleDto> getCourseInfoModuleDtoSet(Course course) {
    return course.getModules().stream()
        .map(
            (module ->
                new CourseInfoModuleDto(
                    module.getTitle(),
                    module.getDescription(),
                    getCourseInfoModuleItemDtoSet(module))))
        .collect(Collectors.toSet());
  }

  private static CourseInfoMentorDto getCourseInfoMentorDto(Course course) {
    String companyName = null;
    Company company = course.getMentor().getCompany();
    if (company != null) {
      companyName = company.getCompanyName();
    }
    return new CourseInfoMentorDto(
        course.getMentor().getName(),
        course.getMentor().getSurname(),
        course.getMentor().getUserImgPath(),
        course.getMentor().getLinkedinPath(),
        companyName);
  }

  private static double getCourseDurationInHours(Course course) {

    double estimatedTime =
        course.getModules().stream()
            .flatMapToDouble(
                module ->
                    module.getModuleItems().stream().mapToDouble(ModuleItem::getEstimatedTime))
            .sum();

    return getRoundedHours(estimatedTime);
  }

  private static double getRoundedHours(double minutes) {

    return minutes == 0.0 ? 0.0 : Math.round((minutes / 60) * 10.0) / 10.0;
  }

  private static Set<CourseInfoModuleItemDto> getCourseInfoModuleItemDtoSet(Module module) {
    return module.getModuleItems().stream()
        .map(
            moduleItem ->
                new CourseInfoModuleItemDto(
                    moduleItem.getModuleItemType().getType(),
                    moduleItem.getLink(),
                    getRoundedHours(moduleItem.getEstimatedTime())))
        .collect(Collectors.toSet());
  }

  private static Long getEnrolledCourseId(Course course) {

    String currentUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();

    Optional<EnrolledCourse> optionalEnrolledCourse =
        course.getEnrolledCourses().stream()
            .filter(
                enrolledCourse ->
                    enrolledCourse.getUser().getEmail().equals(currentUserEmail)
                        && enrolledCourse.getCourse().getId().equals(course.getId()))
            .findFirst();

    return optionalEnrolledCourse.map(EnrolledCourse::getId).orElse(null);
  }
}
