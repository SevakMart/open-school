package app.openschool.coursemanagement.repository;

import app.openschool.coursemanagement.entity.Language;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {
}
