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

  EnrolledCourse findEnrolledCourseById(Long enrolledCourseId);

  Course enrollCourse(User user, long courseId);

  List<EnrolledCourse> findEnrolledCourses(Long userId, Long courseStatusId);

  Page<Course> findSavedCourses(Long userId, Pageable pageable);

  Course saveCourse(Long userId, Long courseId);

  Course deleteCourse(Long userId, Long courseId);

  Page<Course> findMentorCourses(Long mentorId, Pageable page);

  Page<User> findMentorsByName(String name, Pageable pageable);

  User saveMentor(User user, Long mentorId);

  Page<User> findSavedMentors(User user, Pageable pageable);

  Page<User> findSavedMentorsByName(Long userId, String name, Pageable pageable);

  void deleteMentor(User user, Long mentorId);

  void completeEnrolledModuleItem(Long enrolledModuleItemId);
}
