package app.openschool.user;

import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.course.api.dto.CourseDto;
import app.openschool.course.api.dto.UserCourseDto;
import app.openschool.user.api.dto.MentorDto;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  Page<MentorDto> findAllMentors(Pageable pageable);

  List<CourseDto> getSuggestedCourses(Long userId);

  Set<PreferredCategoryDto> savePreferredCategories(Long userId, Set<Long> categoryIds);

  List<UserCourseDto> findEnrolledCourses(Long courseStatusId);

  Page<CourseDto> findSavedCourses(Pageable pageable);

  Optional<Long> saveCourse(Long courseId);

  Optional<Long> deleteCourse(Long courseId);
}
