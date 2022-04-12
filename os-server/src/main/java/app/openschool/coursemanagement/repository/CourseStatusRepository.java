package app.openschool.coursemanagement.repository;

import app.openschool.coursemanagement.entity.CourseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseStatusRepository extends JpaRepository<CourseStatus, Long> {}
