package app.openschool.course;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import app.openschool.common.security.JwtTokenProvider;
import app.openschool.common.security.UserPrincipal;
import app.openschool.course.api.CourseGenerator;
import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.faq.Faq;
import app.openschool.faq.FaqServiceImpl;
import app.openschool.faq.api.FaqGenerator;
import app.openschool.faq.api.dto.CreateFaqRequest;
import app.openschool.user.User;
import app.openschool.user.role.Role;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class CourseControllerTest {

  @Autowired private MockMvc mockMvc;

  @Autowired private JwtTokenProvider jwtTokenProvider;

  @MockBean private CourseServiceImpl courseService;

  @MockBean private FaqServiceImpl faqService;

  @Test
  void getCourseInfoUnauthorized() throws Exception {
    mockMvc
        .perform(get("/api/v1/courses/1").contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void getCourseWithWrongCourseId() throws Exception {
    when(courseService.findCourseById(1L)).thenThrow(new IllegalArgumentException());
    User user = new User("Test", "pass");
    user.setRole(new Role("STUDENT"));
    String jwt = "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
    mockMvc
        .perform(
            get("/api/v1/courses/1").contentType(APPLICATION_JSON).header("Authorization", jwt))
        .andExpect(status().isBadRequest());
  }

  @Test
  void getCourseWithRightCourseId() throws Exception {
    when(courseService.findCourseById(1L))
        .thenReturn(
            CourseMapper.toCourseInfoDto(CourseGenerator.generateCourseWithEnrolledCourses()));
    User user = new User("Test", "pass");
    user.setRole(new Role("STUDENT"));
    String jwt = "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
    mockMvc
        .perform(
            get("/api/v1/courses/1").contentType(APPLICATION_JSON).header("Authorization", jwt))
        .andExpect(status().isOk());
  }

  @Test
  void searchCourses() throws Exception {
    when(courseService.findAll(null, null, null, null, PageRequest.of(0, 2)))
        .thenReturn(new PageImpl<>(List.of(), PageRequest.of(0, 2), 2));
    mockMvc
        .perform(
            get("/api/v1/courses/searched")
                .queryParam("page", "0")
                .queryParam("size", "2")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findAllFaqs_unauthorized() throws Exception {

    Pageable pageable = PageRequest.of(0, 5);
    PageImpl<Faq> faqs = FaqGenerator.generateFaqPage(pageable);
    when(faqService.findAll(pageable)).thenReturn(faqs);

    mockMvc
        .perform(
            get("/api/v1/courses/faqs")
                .queryParam("page", "o")
                .queryParam("size", "5")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void findAllFaqs_withoutAdminRole() throws Exception {

    Pageable pageable = PageRequest.of(0, 5);
    PageImpl<Faq> faqs = FaqGenerator.generateFaqPage(pageable);
    when(faqService.findAll(pageable)).thenReturn(faqs);

    // Role- MENTOR
    String jwt = generateJwtToken();
    mockMvc
        .perform(
            get("/api/v1/courses/faqs")
                .queryParam("page", "o")
                .queryParam("size", "5")
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isForbidden());
  }

  @Test
  void findFaqsByCourseId_authorized() throws Exception {

    long courseId = 1L;
    Faq faq = FaqGenerator.generateFaq();
    faq.getCourse().setId(courseId);
    Page<Faq> faqs = new PageImpl<>(List.of(faq));

    Pageable pageable = PageRequest.of(0, 5);

    when(faqService.findFaqsByCourseId(courseId, pageable)).thenReturn(faqs);

    String jwt = generateJwtToken();
    mockMvc
        .perform(
            get("/api/v1/courses/faqs/1")
                .queryParam("courseId", "1")
                .queryParam("page", "o")
                .queryParam("size", "5")
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isOk());
  }

  @Test
  void findFaqsByCourseId_unauthorized() throws Exception {

    Page<Faq> faqs = new PageImpl<>(List.of(FaqGenerator.generateFaq()));

    Pageable pageable = PageRequest.of(0, 5);

    when(faqService.findFaqsByCourseId(1L, pageable)).thenReturn(faqs);
    mockMvc
        .perform(
            get("/api/v1/courses/faqs/1")
                .queryParam("courseId", "1")
                .queryParam("page", "o")
                .queryParam("size", "5")
                .contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void addFaq_unauthorized() throws Exception {

    CreateFaqRequest request = new CreateFaqRequest("question", "answer", 1L);
    String email = "poxos@gm.com";
    String requestBody = "{\"question\": \"question\",\"answer\": \"answer\", \"courseId\": 1}";
    when(faqService.add(request, email)).thenReturn(new Faq());

    mockMvc
        .perform(post("/api/v1/courses/faqs").content(requestBody).contentType(APPLICATION_JSON))
        .andExpect(status().isUnauthorized());
  }

  @Test
  void deleteFaq() throws Exception {

    String jwt = generateJwtToken();
    doNothing().when(faqService).delete(any(), anyString());
    mockMvc
        .perform(
            delete("/api/v1/courses/faqs/1")
                .header("Authorization", jwt)
                .contentType(APPLICATION_JSON))
        .andExpect(status().isNoContent());
  }

  private String generateJwtToken() {
    User user = new User("Test", "pass");
    user.setRole(new Role("MENTOR"));
    return "Bearer " + jwtTokenProvider.generateJwtToken(new UserPrincipal(user));
  }
}
