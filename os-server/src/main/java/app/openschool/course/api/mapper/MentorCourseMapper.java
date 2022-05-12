package app.openschool.course.api.mapper;

import app.openschool.course.Course;
import app.openschool.course.api.dto.MentorCourseDto;
import app.openschool.course.keyword.Keyword;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class MentorCourseMapper {

  public static MentorCourseDto toMentorCourseDto(Course course) {
    Set<String> keywords =
        course.getKeywords().stream().map(Keyword::getTitle).collect(Collectors.toSet());
    return new MentorCourseDto(
        course.getTitle(),
        course.getDescription(),
        course.getRating(),
        course.getCategory().getTitle(),
        course.getDifficulty().getTitle(),
        course.getLanguage().getTitle(),
        keywords);
  }

  public static Page<MentorCourseDto> toMentorDtoPage(Page<Course> coursePage) {
    List<Course> courseList = coursePage.toList();
    List<MentorCourseDto> mentorCourseDtoList = new ArrayList<>();
    for (Course course : courseList) {
      mentorCourseDtoList.add(toMentorCourseDto(course));
    }
    return new PageImpl<>(
        mentorCourseDtoList, coursePage.getPageable(), coursePage.getTotalElements());
  }
}
