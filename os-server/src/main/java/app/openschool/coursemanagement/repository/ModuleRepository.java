package app.openschool.coursemanagement.repository;

import app.openschool.coursemanagement.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {}