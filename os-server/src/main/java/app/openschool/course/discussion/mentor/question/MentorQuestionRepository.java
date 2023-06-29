package app.openschool.course.discussion.mentor.question;

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
public interface MentorQuestionRepository extends JpaRepository<MentorQuestion, Long> {

  @Query(
      value =
          "SELECT * FROM mentor_question "
              + "WHERE mentor_question.learning_path_id = "
              + "(SELECT learning_path_id FROM enrolled_learning_path "
              + "WHERE id =:enrolledCourseId)",
      nativeQuery = true)
  Page<MentorQuestion> findQuestionByEnrolledCourseId(
      @Param("enrolledCourseId") Long enrolledCourseId, Pageable pageable);

  @Query(
      value =
          "SELECT * FROM mentor_question "
              + "WHERE mentor_question.id =:questionId AND "
              + "mentor_question.learning_path_id = "
              + "(SELECT learning_path_id FROM enrolled_learning_path "
              + "WHERE id =:enrolledCourseId)",
      nativeQuery = true)
  Optional<MentorQuestion> findQuestionByIdAndEnrolledCourseId(
      @Param("enrolledCourseId") Long enrolledCourseId, @Param("questionId") Long questionId);

  @Modifying(flushAutomatically = true, clearAutomatically = true)
  @Transactional
  @Query(
      value =
          "DELETE FROM mentor_question "
              + "WHERE mentor_question.id =:questionId AND "
              + "mentor_question.learning_path_id = "
              + "(SELECT learning_path_id FROM enrolled_learning_path "
              + "WHERE id =:enrolledCourseId AND mentor_question.user_id = "
              + "(SELECT id FROM user WHERE email =:userEmail)); ",
      nativeQuery = true)
  int delete(
      @Param("questionId") Long questionId,
      @Param("userEmail") String userEmail,
      @Param("enrolledCourseId") Long enrolledCourseId);

  @Query(
      value =
          "SELECT * FROM mentor_question "
              + "WHERE mentor_question.id =:questionId AND "
              + "mentor_question.learning_path_id = "
              + "(SELECT learning_path_id FROM enrolled_learning_path "
              + "WHERE id =:enrolledCourseId AND mentor_question.user_id = "
              + "(SELECT id FROM user WHERE email =:userEmail)); ",
      nativeQuery = true)
  Optional<MentorQuestion> findMentorQuestionByIdAndUserEmailAndEnrolledCourseId(
      @Param("questionId") Long questionId,
      @Param("userEmail") String userEmail,
      @Param("enrolledCourseId") Long enrolledCourseId);
}
