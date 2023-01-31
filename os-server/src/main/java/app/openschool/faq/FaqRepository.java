package app.openschool.faq;

import app.openschool.faq.api.dto.CreateFaqRequest;
import app.openschool.faq.api.dto.UpdateFaqDtoRequest;
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
public interface FaqRepository extends JpaRepository<Faq, Long> {
  Page<Faq> findFaqsByCourseId(Long courseId, Pageable pageable);

  @Modifying
  @Transactional
  @Query(
      value =
          "INSERT INTO faq(question, answer, learning_path_id) "
              + "  VALUES ( :#{#req.question} , :#{#req.answer} , "
              + "  (SELECT learning_path.id FROM learning_path "
              + "  INNER JOIN user ON "
              + "  (user.id = learning_path.mentor_id "
              + "  AND user.email =:email AND learning_path.id = :#{#req.courseId})));",
      nativeQuery = true)
  int saveFaq(@Param("req") CreateFaqRequest request, @Param("email") String mentorEmail);

  @Query(value = "SELECT * FROM faq ORDER BY faq.id DESC LIMIT 1;", nativeQuery = true)
  Optional<Faq> getLastInsertData();

  @Modifying
  @Transactional
  @Query(
      value =
          "UPDATE faq SET faq.question = :#{#update.question} , faq.answer = :#{#update.answer} "
              + "WHERE faq.id = :id AND faq.learning_path_id IN "
              + "(SELECT learning_path.id FROM learning_path "
              + "INNER JOIN user ON (user.id = learning_path.mentor_id AND user.email = :email ));",
      nativeQuery = true)
  int updateFaq(
      @Param("update") UpdateFaqDtoRequest update,
      @Param("id") Long faqId,
      @Param("email") String mentorEmail);

  @Modifying
  @Transactional
  @Query(
      value =
          "DELETE FROM faq "
              + "WHERE faq.id = :id AND faq.learning_path_id IN "
              + "(SELECT learning_path.id FROM learning_path "
              + "INNER JOIN user ON (user.id = learning_path.mentor_id AND user.email = :email ));",
      nativeQuery = true)
  int deleteFaq(@Param("id") Long faqId, @Param("email") String mentorEmail);
}
