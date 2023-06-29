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
              + "WHERE id =:enrolledCourseId)" + "AND (:q IS NOT NULL AND LENGTH(:q) >= 3 AND mentor_question.text LIKE CONCAT('%', :q, '%'))",
      nativeQuery = true)
  Page<MentorQuestion> findQuestionByEnrolledCourseId(
      @Param("enrolledCourseId") Long enrolledCourseId, Pageable pageable, @Param("q") String q);

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
