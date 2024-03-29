package app.openschool.course;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, Long> {
  Optional<EnrolledCourse> findByUserEmailAndCourseId(String email, Long courseId);

  boolean existsByIdAndUserEmail(Long enrolledCourseId, String userEmail);
}
