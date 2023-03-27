package app.openschool.course.discussion.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.discussion.TestHelper;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.mapper.QuestionMapper;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.user.api.mapper.UserMapper;
import org.junit.jupiter.api.Test;

public class QuestionMapperTest {

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
