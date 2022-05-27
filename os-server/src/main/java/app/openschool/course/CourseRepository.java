package app.openschool.course;

import java.util.List;
import java.util.Optional;
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

  Optional<Course> findById(Long id);
}
