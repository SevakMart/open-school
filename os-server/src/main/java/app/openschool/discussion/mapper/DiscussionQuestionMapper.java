package app.openschool.discussion.mapper;

import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.discussion.DiscussionQuestion;
import app.openschool.discussion.dto.DiscussionQuestionResponseDto;
import app.openschool.user.api.mapper.UserMapper;

public class DiscussionQuestionMapper {

  public static DiscussionQuestionResponseDto toResponseDto(DiscussionQuestion discussionQuestion) {
    return new DiscussionQuestionResponseDto(
        discussionQuestion.getId(),
        discussionQuestion.getText(),
        UserMapper.toUserDto(discussionQuestion.getUser()),
        CourseMapper.toCourseDto(discussionQuestion.getCourse()),
        discussionQuestion.getCreatedDate());
  }
}
