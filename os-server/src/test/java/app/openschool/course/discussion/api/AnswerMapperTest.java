package app.openschool.course.discussion.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.course.discussion.mapper.AnswerMapper;
import app.openschool.course.discussion.peers.answer.PeersAnswer;
import app.openschool.user.api.mapper.UserMapper;
import org.junit.jupiter.api.Test;

public class AnswerMapperTest {

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
