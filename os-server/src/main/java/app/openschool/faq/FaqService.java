package app.openschool.faq;

import app.openschool.faq.api.dto.CreateFaqRequest;
import app.openschool.faq.api.dto.UpdateFaqDtoRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FaqService {
  Page<Faq> findAll(Pageable pageable);

  Page<Faq> findFaqsByCourseId(Long courseId, Pageable pageable);

  Faq add(CreateFaqRequest request, String mentorEmail)
      throws SQLIntegrityConstraintViolationException;

  Faq update(UpdateFaqDtoRequest request, Long faqId, String mentorEmail);

  void delete(Long faqId, String mentorEmail);
}
