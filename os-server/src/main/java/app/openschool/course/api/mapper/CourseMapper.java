package app.openschool.course.api.mapper;

import app.openschool.category.Category;
import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.CourseInfoDto;
import app.openschool.course.api.dto.CourseInfoMentorDto;
import app.openschool.course.api.dto.CourseInfoModuleDto;
import app.openschool.course.api.dto.CourseInfoModuleItemDto;
import app.openschool.course.api.dto.CreateCourseRequest;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.keyword.Keyword;
import app.openschool.course.language.Language;
import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.Module;
import app.openschool.course.module.api.mapper.ModuleMapper;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.status.CourseStatus;
import app.openschool.user.User;
import app.openschool.user.company.Company;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

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
        course.getTitle(),
        course.getDescription(),
        course.getGoal(),
        getCourseInfoModuleDtoSet(course),
        getCourseInfoMentorDto(course),
        course.getRating(),
        course.getEnrolledCourses().size(),
        course.getDifficulty().getTitle(),
        course.getLanguage().getTitle(),
        getCourseDuration(course));
  }

  public static EnrolledCourse toEnrolledCourse(Course course, User user) {
    EnrolledCourse enrolledCourse =
        new EnrolledCourse(LocalDate.now(), course, user, CourseStatus.inProgress());
    Set<EnrolledModule> enrolledModules =
        EnrolledModuleMapper.toEnrolledModules(course, enrolledCourse);
    enrolledCourse.setEnrolledModules(enrolledModules);
    return enrolledCourse;
  }

  public static Course toCourse(CreateCourseRequest request, User mentor) {
    Course course = new Course();
    course.setTitle(request.getTitle());
    course.setDescription(request.getDescription());
    course.setGoal(request.getGoal());
    Category category = new Category();
    category.setId(request.getCategoryId());
    course.setCategory(category);
    Difficulty difficulty = new Difficulty();
    difficulty.setId(request.getDifficultyId());
    course.setDifficulty(difficulty);
    Language language = new Language();
    language.setId(request.getLanguageId());
    course.setLanguage(language);
    course.setMentor(mentor);
    Set<Keyword> keywords =
        request.getKeywordIds().stream()
            .map(
                requestKeyword -> {
                  Keyword keyword = new Keyword();
                  keyword.setId(requestKeyword);
                  return keyword;
                })
            .collect(Collectors.toSet());
    course.setKeywords(keywords);
    course.setModules(ModuleMapper.toModules(request.getCreateModuleRequests(), course));
    return course;
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

  private static long getCourseDuration(Course course) {
    return course.getModules().stream()
        .flatMapToLong(
            module -> module.getModuleItems().stream().mapToLong(ModuleItem::getEstimatedTime))
        .sum();
  }

  private static Set<CourseInfoModuleItemDto> getCourseInfoModuleItemDtoSet(Module module) {
    return module.getModuleItems().stream()
        .map(
            moduleItem ->
                new CourseInfoModuleItemDto(
                    moduleItem.getModuleItemType().getType(), moduleItem.getLink()))
        .collect(Collectors.toSet());
  }
}
