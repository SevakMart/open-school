package app.openschool.coursemanagement.repository;

import app.openschool.coursemanagement.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {}
