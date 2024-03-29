package app.openschool.course.discussion.mentor.question;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MentorQuestionRepository extends JpaRepository<MentorQuestion, Long> {

  @Query(
      value =
          "SELECT * FROM mentor_question "
              + "WHERE mentor_question.learning_path_id = "
              + "(SELECT learning_path_id FROM enrolled_learning_path "
              + "WHERE id =:enrolledCourseId) AND user_id = "
              + "(SELECT id FROM user WHERE email = :userEmail) "
              + "AND (COALESCE(:searchQuery) IS NULL "
              + "OR mentor_question.`text` LIKE CONCAT('%', :searchQuery, '%'))",
      nativeQuery = true)
  Page<MentorQuestion> findQuestionByEnrolledCourseId(
      @Param("enrolledCourseId") Long enrolledCourseId,
      @Param("userEmail") String userEmail,
      Pageable pageable,
      @Param("searchQuery") String searchQuery);

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
}
