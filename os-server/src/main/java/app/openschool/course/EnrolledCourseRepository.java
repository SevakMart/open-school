package app.openschool.course;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, Long> {

  Optional<EnrolledCourse> findByCourseId(Long id);
}
