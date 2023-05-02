package app.openschool.course.discussion.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.course.discussion.mapper.AnswerMapper;
import app.openschool.course.discussion.peers.answer.PeersAnswer;
import app.openschool.user.api.mapper.UserMapper;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;


class AnswerMapperTest {

  @Test
  void toAnswerDtoPage() {
    Page<PeersAnswer> answerPage =
        new PageImpl<>(List.of(TestHelper.createDiscussionPeersAnswer()));
    Page<AnswerResponseDto> expectedResult = AnswerMapper.toAnswerDtoPage(answerPage);
    String className = AnswerResponseDto.class.getName();

    assertTrue(
        expectedResult.stream()
            .allMatch(content -> content.getClass().getName().equals(className)));

    assertThat(expectedResult.getContent().stream().findAny().get()).hasFieldOrProperty("text");
  }

  @Test
  void toAnswerDto() {
    PeersAnswer answer = TestHelper.createDiscussionPeersAnswer();
    AnswerResponseDto expected = AnswerMapper.toAnswerDto(answer);

    assertEquals(expected.getId(), answer.getId());
    assertEquals(expected.getText(), answer.getText());
    assertEquals(expected.getUserDto(), UserMapper.toUserDto(answer.getUser()));
    assertEquals(expected.getCreatedDate(), answer.getCreatedDate());
  }
}
