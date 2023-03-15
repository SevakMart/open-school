package app.openschool.course.discussion.peers.question;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeersQuestionRepository extends JpaRepository<PeersQuestion, Long> {}
