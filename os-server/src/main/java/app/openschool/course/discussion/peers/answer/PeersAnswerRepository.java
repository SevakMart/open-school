package app.openschool.course.discussion.peers.answer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeersAnswerRepository extends JpaRepository<PeersAnswer, Long> {}
