package app.openschool.coursemanagement;

import app.openschool.coursemanagement.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
  @Query(
      value =
          "SELECT * FROM category_user cu JOIN category c ON cu.category_id = c.id "
              + "JOIN learning_path lp ON c.id = lp.id"
              + "JOIN difficulty d ON d.id = lp.difficulty_id WHERE cu.user_id = :userId  LIMIT = 4",
      nativeQuery = true)
  List<Course> getSuggestedCourses(@RequestParam Long userId);
}
///categories/courses/suggested?userId=3
