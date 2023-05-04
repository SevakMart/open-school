package app.openschool.course.discussion.peers.answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeersAnswerRepository extends JpaRepository<PeersAnswer, Long> {

  Page<PeersAnswer> findPeersAnswerByPeersQuestionId(Long questionId, Pageable pageable);
}
