package app.openschool.course.api.mapper;

import app.openschool.course.Course;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.keyword.Keyword;
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
        course.getTitle(), course.getRating(), course.getDifficulty().getTitle(), keywords);
  }

  public static Page<CourseDto> toCourseDtoPage(Page<Course> coursePage) {
    List<Course> courseList = coursePage.toList();
    List<CourseDto> courseDtoList = new ArrayList<>();
    for (Course course : courseList) {
      courseDtoList.add(toCourseDto(course));
    }
    return new PageImpl<>(courseDtoList, coursePage.getPageable(), coursePage.getTotalElements());
  }
}
