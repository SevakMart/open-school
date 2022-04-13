package app.openschool.coursemanagement.repository;

import app.openschool.coursemanagement.entity.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DifficultyRepository extends JpaRepository<Difficulty, Integer> {
}
