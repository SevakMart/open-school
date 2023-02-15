package app.openschool.course.discussion.mentor.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorQuestionRepository extends JpaRepository<MentorQuestion, Long> {}
