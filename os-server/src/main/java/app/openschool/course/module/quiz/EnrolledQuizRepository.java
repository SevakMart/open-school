package app.openschool.course.module.quiz;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EnrolledQuizRepository extends JpaRepository<EnrolledQuiz, Long> {}
