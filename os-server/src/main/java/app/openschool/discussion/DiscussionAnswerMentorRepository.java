package app.openschool.discussion;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionAnswerMentorRepository
    extends JpaRepository<DiscussionAnswerMentor, Long> {}
