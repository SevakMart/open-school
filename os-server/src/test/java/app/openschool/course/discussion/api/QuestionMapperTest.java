package app.openschool.course.discussion.api;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.mapper.QuestionMapper;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import org.junit.jupiter.api.Test;

public class QuestionMapperTest {

  @Test
  void toResponseDto() {
    PeersQuestion discussionQuestion = TestHelper.createDiscussionPeersQuestion();
    QuestionResponseDto expectedResult = QuestionMapper.toResponseDto(discussionQuestion);
    assertThat(expectedResult).hasOnlyFields("id", "text", "userDto", "courseDto", "createdDate");
  }
}
