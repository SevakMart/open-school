package app.openschool.user;

import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.UserEnrolledCourseDto;
import app.openschool.user.api.dto.MentorDto;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  Page<MentorDto> findAllMentors(Pageable pageable);

  List<CourseDto> getSuggestedCourses(Long userId);

  Set<PreferredCategoryDto> savePreferredCategories(Long userId, Set<Long> categoryIds);

  List<UserEnrolledCourseDto> findUserEnrolledCourses(Long userId, Long courseStatusId);
}
