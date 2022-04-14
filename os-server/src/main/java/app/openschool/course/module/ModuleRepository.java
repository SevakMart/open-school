package app.openschool.course.module;

import app.openschool.course.module.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {}