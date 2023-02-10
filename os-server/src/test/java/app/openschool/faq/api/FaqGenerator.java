package app.openschool.faq.api;

import app.openschool.course.Course;
import app.openschool.course.api.CourseGenerator;
import app.openschool.faq.Faq;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public class FaqGenerator {

  public static Faq generateFaq() {
    Course course = CourseGenerator.generateCourse();
    Faq faq = new Faq();
    faq.setQuestion("question");
    faq.setAnswer("answer");
    faq.setCourse(course);
    return faq;
  }

  public static PageImpl<Faq> generateFaqPage(Pageable pageable) {
    List<Faq> faqList = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      faqList.add(generateFaq());
    }
    return new PageImpl<>(faqList, pageable, 5);
  }
}
