package app.openschool.course.discussion.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.course.discussion.mapper.AnswerMapper;
import app.openschool.course.discussion.peers.answer.PeersAnswer;
import org.junit.jupiter.api.Test;

public class AnswerMapperTest {

  @Test
  void toAnswerDto() {
    PeersAnswer discussionPeersAnswer = TestHelper.createDiscussionPeersAnswer();
    AnswerResponseDto expectedResult = AnswerMapper.toAnswerDto(discussionPeersAnswer);
    assertThat(expectedResult).hasOnlyFields("id", "text", "userDto", "createdDate");
  }
}
