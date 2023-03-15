package app.openschool.course.discussion.mentor.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorAnswerRepository extends JpaRepository<MentorAnswer, Long> {}
