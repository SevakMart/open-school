package app.openschool.course.discussion.peers.question;

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
public interface PeersQuestionRepository extends JpaRepository<PeersQuestion, Long> {

  @Query(
      value =
          "SELECT * FROM peers_question "
              + "WHERE peers_question.id =:questionId AND "
              + "peers_question.learning_path_id = "
              + "(SELECT learning_path_id FROM enrolled_learning_path "
              + "WHERE id =:enrolledCourseId AND user_id = "
              + "(SELECT id FROM user WHERE email =:userEmail)); ",
      nativeQuery = true)
  Optional<PeersQuestion> findPeersQuestionByIdAndUserEmailAndEnrolledCourseId(
      @Param("questionId") Long questionId,
      @Param("userEmail") String userEmail,
      @Param("enrolledCourseId") Long enrolledCourseId);

  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Transactional
  @Query(
      value =
          "DELETE FROM peers_question "
              + "WHERE peers_question.id =:questionId AND "
              + "peers_question.learning_path_id = "
              + "(SELECT learning_path_id FROM enrolled_learning_path "
              + "WHERE id =:enrolledCourseId AND user_id = "
              + "(SELECT id FROM user WHERE email =:userEmail)); ",
      nativeQuery = true)
  int delete(
      @Param("questionId") Long questionId,
      @Param("userEmail") String userEmail,
      @Param("enrolledCourseId") Long enrolledCourseId);

  @Query(
      value =
          "SELECT * FROM peers_question "
              + "WHERE peers_question.learning_path_id = "
              + "(SELECT learning_path_id FROM enrolled_learning_path "
              + "WHERE id =:enrolledCourseId)",
      nativeQuery = true)
  Page<PeersQuestion> findQuestionByCourseId(
      @Param("enrolledCourseId") Long enrolledCourseId, Pageable pageable);

  @Query(
      value =
          "SELECT * FROM peers_question "
              + "WHERE peers_question.id =:questionId AND "
              + "peers_question.learning_path_id = "
              + "(SELECT learning_path_id FROM enrolled_learning_path "
              + "WHERE id =:enrolledCourseId)",
      nativeQuery = true)
  Optional<PeersQuestion> findQuestionByIdAndEnrolledCourseId(
      @Param("enrolledCourseId") Long enrolledCourseId, @Param("questionId") Long questionId);
}
