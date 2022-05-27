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
          "SELECT * FROM learning_path WHERE "
              + "(:courseTitle IS NULL OR LOWER(title) LIKE LOWER(CONCAT('%', :courseTitle, '%'))) "
              + "AND ((:subCategoryIds) IS NULL OR category_id IN (:subCategoryIds)) "
              + "AND ((:languageIds) IS NULL OR language_id IN (:languageIds)) "
              + "AND ((:difficultyIds) IS NULL OR difficulty_id IN (:difficultyIds))",
      nativeQuery = true)
  Page<Course> searchCourses(
      Pageable pageable,
      @Param(value = "courseTitle") String courseTitle,
      @Param(value = "subCategoryIds") List<Long> subCategoryIds,
      @Param(value = "languageIds") List<Long> languageIds,
      @Param(value = "difficultyIds") List<Long> difficultyIds);
}
