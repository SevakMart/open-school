package app.openschool.course.discussion.peers.answer;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PeersAnswerRepository extends JpaRepository<PeersAnswer, Long> {

  Page<PeersAnswer> findPeersAnswerByPeersQuestionId(Long questionId, Pageable pageable);

  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Transactional
  @Query(
      value =
          "DELETE FROM peers_answer "
              + "WHERE peers_answer.id =:answerId AND "
              + "peers_answer.peers_question_id = "
              + "(SELECT id FROM peers_question "
              + "WHERE id =:questionId AND "
              + "peers_question.learning_path_id = "
              + "(SELECT learning_path_id FROM enrolled_learning_path "
              + "WHERE id = :enrolledCourseId AND peers_question.user_id = "
              + "(SELECT id FROM user "
              + "WHERE email = :userEmail)));",
      nativeQuery = true)
  int delete(
      @Param("answerId") Long answerId,
      @Param("questionId") Long questionId,
      @Param("enrolledCourseId") Long enrolledCourseId,
      @Param("userEmail") String userEmail);

  @Query(
      value =
          "SELECT * FROM peers_answer "
              + "WHERE peers_answer.id =:answerId AND "
              + "peers_answer.peers_question_id = "
              + "(SELECT id FROM peers_question "
              + "WHERE id =:questionId AND "
              + "peers_question.learning_path_id = "
              + "(SELECT learning_path_id FROM enrolled_learning_path "
              + "WHERE id = :enrolledCourseId AND peers_question.user_id = "
              + "(SELECT id FROM user "
              + "WHERE email = :userEmail)));",
      nativeQuery = true)
  Optional<PeersAnswer> findPeersAnswerByIdAndUserEmailAndQuestionId(
      @Param("answerId") Long answerId,
      @Param("questionId") Long questionId,
      @Param("enrolledCourseId") Long enrolledCourseId,
      @Param("userEmail") String userEmail);
}
