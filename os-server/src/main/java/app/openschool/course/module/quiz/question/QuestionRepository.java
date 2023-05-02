package app.openschool.course.module.quiz.question;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface QuestionRepository extends JpaRepository<Question, Long> {
  Optional<Page<Question>> findAllQuestionsByQuizId(Long id, Pageable pageable);
}
