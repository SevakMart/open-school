package app.openschool.course.discussion.mentor.answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface MentorAnswerRepository extends JpaRepository<MentorAnswer, Long> {
    Page<MentorAnswer> findMentorAnswerByMentorQuestionId(Long questionId, Pageable pageable);

    @Modifying(flushAutomatically = true, clearAutomatically = true)
    @Transactional
    @Query(
            value =
            "DELETE FROM mentor_answer "
                    + "WHERE mentor_answer.id =:answerId AND "
                    + "mentor_answer.mentor_question_id = "
                    + "(SELECT id FROM mentor_question "
                    + "WHERE id =:questionId AND "
                    + "mentor_question.learning_path_id = "
                    + "(SELECT learning_path_id FROM enrolled_learning_path "
                    + "WHERE id = :enrolledCourseId AND mentor_question.user_id = "
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
                    "SELECT * FROM mentor_answer "
                            + "WHERE mentor_answer.id =:answerId AND "
                            + "mentor_answer.mentor_question_id = "
                            + "(SELECT id FROM mentor_question "
                            + "WHERE id =:questionId AND "
                            + "mentor_question.learning_path_id = "
                            + "(SELECT learning_path_id FROM enrolled_learning_path "
                            + "WHERE id = :enrolledCourseId AND mentor_question.user_id = "
                            + "(SELECT id FROM user "
                            + "WHERE email = :userEmail)));",
            nativeQuery = true)
    Optional<MentorAnswer> findMentorAnswerByIdAndUserEmailAndQuestionId(
            @Param("answerId") Long answerId,
            @Param("questionId") Long questionId,
            @Param("enrolledCourseId") Long enrolledCourseId,
            @Param("userEmail") String userEmail);
}
