package app.openschool.user;

import app.openschool.category.api.dto.PreferredCategoryDto;
import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {

  Page<User> findAllMentors(Pageable pageable);

  List<Course> getSuggestedCourses(Long userId);

  Set<PreferredCategoryDto> savePreferredCategories(Long userId, Set<Long> categoryIds);

  EnrolledCourse findEnrolledCourseOverview(Long enrolledCourseId);

  List<EnrolledCourse> findEnrolledCourses(Long userId, Long courseStatusId);

  Page<Course> findSavedCourses(Long userId, Pageable pageable);

  Course saveCourse(Long userId, Long courseId);

  Course deleteCourse(Long userId, Long courseId);

  Page<Course> findMentorCourses(Long mentorId, Pageable page);
}
