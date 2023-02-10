package app.openschool.course;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnrolledCourseRepository extends JpaRepository<EnrolledCourse, Long> {

  Optional<EnrolledCourse> findByCourseId(Long id);
}
