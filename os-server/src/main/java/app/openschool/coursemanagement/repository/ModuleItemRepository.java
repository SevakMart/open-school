package app.openschool.coursemanagement.repository;

import app.openschool.coursemanagement.entity.ModuleItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleItemRepository extends JpaRepository<ModuleItem, Long> {}