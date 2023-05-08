package app.openschool.course.discussion.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.discussion.Question;
import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.mapper.QuestionMapper;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.user.api.mapper.UserMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

class QuestionMapperTest {

  @Test
  void toQuestionDtoPage() {
    Page<Question> questionPage =
        new PageImpl<>(List.of(TestHelper.createDiscussionPeersQuestion()));
    Page<QuestionResponseDto> expectedResult = QuestionMapper.toQuestionDtoPage(questionPage);
    String className = QuestionResponseDto.class.getName();

    assertTrue(
        expectedResult.stream()
            .allMatch(content -> content.getClass().getName().equals(className)));

    assertThat(expectedResult.getContent().stream().findAny().get()).hasFieldOrProperty("text");
  }

  @Test
  void toResponseDto() {
    PeersQuestion discussionQuestion = TestHelper.createDiscussionPeersQuestion();
    QuestionResponseDto expectedResult = QuestionMapper.toResponseDto(discussionQuestion);

    assertEquals(expectedResult.getId(), discussionQuestion.getId());
    assertEquals(expectedResult.getText(), discussionQuestion.getText());
    assertEquals(expectedResult.getUserDto(), UserMapper.toUserDto(discussionQuestion.getUser()));
    assertEquals(
        expectedResult.getCourseDto().getId(),
        CourseMapper.toCourseDto(discussionQuestion.getCourse()).getId());
    assertEquals(expectedResult.getCreatedDate(), discussionQuestion.getCreatedDate());
  }
}
