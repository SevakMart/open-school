package app.openschool.faq.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import app.openschool.faq.Faq;
import app.openschool.faq.api.dto.FaqDto;
import app.openschool.faq.api.mapper.FaqMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public class FaqMapperTest {

  @BeforeEach
  public void setup() {

    Authentication authentication = Mockito.mock(Authentication.class);
    SecurityContext securityContext = Mockito.mock(SecurityContext.class);
    SecurityContextHolder.setContext(securityContext);
    SecurityContextHolder.getContext().setAuthentication(authentication);

    when(SecurityContextHolder.getContext().getAuthentication()).thenReturn(authentication);
    when(SecurityContextHolder.getContext().getAuthentication().getName()).thenReturn("email");
  }

  @Test
  void toFaqDtoPage() {

    Page<Faq> faqPage = new PageImpl<>(List.of(FaqGenerator.generateFaq()));

    Page<FaqDto> expectedResult = FaqMapper.toFaqDtoPage(faqPage);
    String className = FaqDto.class.getName();

    assertThat(expectedResult).hasOnlyFields("total");

    assertTrue(
        expectedResult.stream()
            .allMatch(content -> content.getClass().getName().equals(className)));
  }

  @Test
  void toFaqDto() {

    Faq faq = FaqGenerator.generateFaq();

    FaqDto expectedResult = FaqMapper.toFaqDto(faq);
    assertThat(expectedResult).hasOnlyFields("id", "question", "answer", "courseInfoDto");
  }
}
