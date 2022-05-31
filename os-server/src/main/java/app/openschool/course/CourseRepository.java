package app.openschool.course;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
  @Query(
      value =
          "SELECT * FROM learning_path lp JOIN category c ON lp.category_id = c.id "
              + "JOIN category_user cu ON c.id = cu.category_id "
              + "WHERE cu.user_id = ?1 ORDER BY lp.rating DESC LIMIT 4",
      nativeQuery = true)
  List<Course> getSuggestedCourses(Long userId);

  @Query(value = "SELECT * FROM learning_path ORDER BY rating DESC LIMIT ?1", nativeQuery = true)
  List<Course> getRandomSuggestedCourses(int limit);

  @Query(
      value = "SELECT * FROM learning_path WHERE id NOT IN (?1) ORDER BY rating DESC LIMIT ?2",
      nativeQuery = true)
  List<Course> getRandomSuggestedCoursesIgnoredExistingCourses(
      List<Long> existingCoursesIds, int limit);

  @Query(
      value =
          "SELECT * FROM learning_path WHERE "
              + "(?1 IS NULL OR LOWER(title) LIKE LOWER(CONCAT('%', ?1, '%'))) "
              + "AND ((?2) IS NULL OR category_id IN (?2)) "
              + "AND ((?3) IS NULL OR language_id IN (?3)) "
              + "AND ((?4) IS NULL OR difficulty_id IN (?4))",
      nativeQuery = true)
  Page<Course> searchCourses(
      Pageable pageable,
      String courseTitle,
      List<Long> subCategoryIds,
      List<Long> languageIds,
      List<Long> difficultyIds);

  @Query(
      value =
          "SELECT * FROM learning_path lp JOIN user_saved_learning_paths ulp "
              + "ON lp.id = ulp.learning_path_id WHERE ulp.user_id = ?1",
      nativeQuery = true)
  Page<Course> findUserSavedCourses(Pageable pageable, Long userId);

  @Transactional
  @Modifying
  @Query(value = "INSERT INTO user_saved_learning_paths VALUES (?1, ?2)", nativeQuery = true)
  void saveCourse(Long userId, Long courseId);
}
