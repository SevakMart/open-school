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
          "INSERT INTO FAQ(QUESTION, ANSWER, LEARNING_PATH_ID) "
              + "  VALUES ( :#{#req.question} , :#{#req.answer} , "
              + "  (SELECT LEARNING_PATH.ID FROM LEARNING_PATH "
              + "  INNER JOIN USER ON "
              + "  (USER.ID = LEARNING_PATH.MENTOR_ID "
              + "  AND USER.EMAIL =:email AND LEARNING_PATH.ID = :#{#req.courseId})));",
      nativeQuery = true)
  int saveFaq(@Param("req") CreateFaqRequest request, @Param("email") String mentorEmail);

  @Query(value = "SELECT * FROM FAQ ORDER BY FAQ.ID DESC LIMIT 1;", nativeQuery = true)
  Optional<Faq> getLastInsertData();

  @Modifying
  @Transactional
  @Query(
      value =
          "UPDATE FAQ SET FAQ.QUESTION = :#{#update.question} , FAQ.ANSWER = :#{#update.answer} "
              + "WHERE FAQ.ID = :id AND FAQ.LEARNING_PATH_ID IN "
              + "(SELECT LEARNING_PATH.ID FROM LEARNING_PATH "
              + "INNER JOIN USER ON (USER.ID = LEARNING_PATH.MENTOR_ID AND USER.EMAIL = :email ));",
      nativeQuery = true)
  int updateFaq(
      @Param("update") UpdateFaqDtoRequest update,
      @Param("id") Long faqId,
      @Param("email") String mentorEmail);

  @Modifying
  @Transactional
  @Query(
      value =
          "DELETE FROM FAQ "
              + "WHERE FAQ.ID = :id AND FAQ.LEARNING_PATH_ID IN "
              + "(SELECT LEARNING_PATH.ID FROM LEARNING_PATH "
              + "INNER JOIN USER ON (USER.ID = LEARNING_PATH.MENTOR_ID AND USER.EMAIL = :email ));",
      nativeQuery = true)
  int deleteFaq(@Param("id") Long faqId, @Param("email") String mentorEmail);
}
