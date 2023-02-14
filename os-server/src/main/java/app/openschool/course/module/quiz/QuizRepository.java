package app.openschool.course.module.quiz;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
  Page<Quiz> findAllByModuleId(Long id, Pageable pageable);
}

