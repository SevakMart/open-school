package app.openschool.course;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
  @Query(
      value =
          "SELECT * FROM learning_path lp JOIN category c ON lp.category_id = c.id "
              + "JOIN category_user cu ON c.id = cu.category_id "
              + "WHERE cu.user_id = :userId ORDER BY lp.rating DESC LIMIT 4",
      nativeQuery = true)
  List<Course> getSuggestedCourses(@Param(value = "userId") Long userId);

  @Query(
      value = "SELECT * FROM learning_path ORDER BY rating DESC LIMIT :limit",
      nativeQuery = true)
  List<Course> getRandomSuggestedCourses(@Param(value = "limit") int limit);

  @Query(
      value =
          "SELECT * FROM learning_path WHERE id NOT IN (:existingCoursesIds) "
              + "ORDER BY rating DESC LIMIT :limit",
      nativeQuery = true)
  List<Course> getRandomSuggestedCoursesIgnoredExistingCourses(
      @Param(value = "limit") int limit,
      @Param(value = "existingCoursesIds") List<Long> existingCoursesIds);

  @Query(
      value =
          "SELECT * FROM learning_path lp JOIN learning_path_student lps "
              + "ON lp.id = lps.learning_path_id "
              + "WHERE lps.user_id = :userId ORDER BY lp.learning_path_status_id",
      nativeQuery = true)
  List<Course> findAllUserCourses(@Param(value = "userId") Long userId);

  @Query(
      value =
          "SELECT * FROM learning_path lp JOIN learning_path_student lps "
              + "ON lp.id = lps.learning_path_id "
              + "WHERE lps.user_id = :userId AND lp.learning_path_status_id = :courseStatusId",
      nativeQuery = true)
  List<Course> findUserCoursesByStatus(
      @Param(value = "userId") Long userId, @Param(value = "courseStatusId") Long courseStatusId);

  @Query(
      value =
          "SELECT * FROM learning_path lp JOIN learning_path_mentor lpm "
              + "ON lp.id = lpm.learning_path_id "
              + "WHERE lpm.user_id = :mentorId",
      nativeQuery = true)
  Page<Course> findCoursesByMentorId(@Param(value = "mentorId") Long mentorId, Pageable page);
}
