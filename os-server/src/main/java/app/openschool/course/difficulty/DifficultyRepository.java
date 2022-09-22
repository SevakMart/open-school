package app.openschool.course.difficulty;

import app.openschool.course.difficulty.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyRepository extends JpaRepository<Difficulty, Integer> {
}
