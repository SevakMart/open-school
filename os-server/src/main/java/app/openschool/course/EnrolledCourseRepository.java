package app.openschool.course;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, Long> {
  @Query(
      value =
          "SELECT * FROM enrolled_learning_path WHERE user_id ="
              + " :userId ORDER BY learning_path_status_id",
      nativeQuery = true)
  List<EnrolledCourse> findAllUserEnrolledCourses(@Param(value = "userId") Long userId);

  @Query(
      value =
          "SELECT * FROM enrolled_learning_path WHERE user_id ="
              + " :userId AND learning_path_status_id = :courseStatusId",
      nativeQuery = true)
  List<EnrolledCourse> findUserEnrolledCoursesByStatus(
      @Param(value = "userId") Long userId, @Param(value = "courseStatusId") Long courseStatusId);
}
