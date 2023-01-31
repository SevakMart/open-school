package app.openschool.faq.api.mapper;

import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.faq.Faq;
import app.openschool.faq.api.dto.FaqDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class FaqMapper {

  private FaqMapper() {
    throw new UnsupportedOperationException();
  }

  public static Page<FaqDto> toFaqDtoPage(Page<Faq> faqPage) {
    List<Faq> faqList = faqPage.toList();
    List<FaqDto> faqDtoList = new ArrayList<>();
    for (Faq faq : faqList) {
      faqDtoList.add(toFaqDto(faq));
    }
    return new PageImpl<>(faqDtoList);
  }

  public static FaqDto toFaqDto(Faq faq) {

    return new FaqDto(
        faq.getId(),
        faq.getQuestion(),
        faq.getAnswer(),
        CourseMapper.toCourseInfoDto(faq.getCourse()));
  }
}
