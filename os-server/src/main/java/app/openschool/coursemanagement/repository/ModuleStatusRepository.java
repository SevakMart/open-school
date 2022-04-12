package app.openschool.coursemanagement.repository;

import app.openschool.coursemanagement.entity.ModuleStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleStatusRepository extends JpaRepository<ModuleStatus, Long> {}