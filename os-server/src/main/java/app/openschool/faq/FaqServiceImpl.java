package app.openschool.faq;

import app.openschool.course.CourseRepository;
import app.openschool.faq.api.dto.CreateFaqRequest;
import app.openschool.faq.api.dto.UpdateFaqDtoRequest;
import java.sql.SQLIntegrityConstraintViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FaqServiceImpl implements FaqService {

  private final FaqRepository faqRepository;
  private final CourseRepository courseRepository;

  public FaqServiceImpl(FaqRepository faqRepository, CourseRepository courseRepository) {
    this.faqRepository = faqRepository;
    this.courseRepository = courseRepository;
  }

  @Override
  public Page<Faq> findAll(Pageable pageable) {
    return faqRepository.findAll(pageable);
  }

  @Override
  @Transactional
  public Page<Faq> findFaqsByCourseId(Long courseId, Pageable pageable) {
    courseRepository.findById(courseId).orElseThrow(IllegalArgumentException::new);
    return faqRepository.findFaqsByCourseId(courseId, pageable);
  }

  @Override
  @Transactional
  public Faq add(CreateFaqRequest request, String mentorEmail)
      throws SQLIntegrityConstraintViolationException {
    int updatedRows = faqRepository.saveFaq(request, mentorEmail);
    if (updatedRows == 1) {
      return faqRepository
          .getLastInsertData()
          .orElseThrow(SQLIntegrityConstraintViolationException::new);
    }
    throw new SQLIntegrityConstraintViolationException();
  }

  @Override
  @Transactional
  public Faq update(UpdateFaqDtoRequest updateFaqDtoRequest, Long faqId, String mentorEmail) {

    int updatedRows = faqRepository.updateFaq(updateFaqDtoRequest, faqId, mentorEmail);
    if (updatedRows == 0) {
      throw new IllegalArgumentException();
    }
    return faqRepository.findById(faqId).orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public void delete(Long faqId, String mentorEmail) {
    int updatedRows = faqRepository.deleteFaq(faqId, mentorEmail);
    if (updatedRows == 0) {
      throw new IllegalArgumentException();
    }
  }
}
