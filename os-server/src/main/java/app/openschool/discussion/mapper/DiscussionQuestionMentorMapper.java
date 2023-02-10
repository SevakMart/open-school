package app.openschool.discussion.mapper;

import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.discussion.DiscussionQuestionMentor;
import app.openschool.discussion.dto.DiscussionQuestionResponseDto;
import app.openschool.user.api.mapper.UserMapper;

public class DiscussionQuestionMentorMapper {
  public static DiscussionQuestionResponseDto toResponseDto(
      DiscussionQuestionMentor discussionQuestionMentor) {
    return new DiscussionQuestionResponseDto(
        discussionQuestionMentor.getId(),
        discussionQuestionMentor.getText(),
        UserMapper.toUserDto(discussionQuestionMentor.getUser()),
        CourseMapper.toCourseDto(discussionQuestionMentor.getCourse()),
        discussionQuestionMentor.getCreatedDate());
  }
}
