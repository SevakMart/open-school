package app.openschool.discussion.mapper;

import app.openschool.discussion.DiscussionAnswerMentor;
import app.openschool.discussion.dto.DiscussionAnswerResponseDto;
import app.openschool.user.api.mapper.UserMapper;

public class DiscussionAnswerMentorMapper {

  public static DiscussionAnswerResponseDto toAnswerDto(
      DiscussionAnswerMentor discussionAnswerMentor) {
    return new DiscussionAnswerResponseDto(
        discussionAnswerMentor.getId(),
        discussionAnswerMentor.getText(),
        UserMapper.toUserDto(discussionAnswerMentor.getUser()),
        discussionAnswerMentor.getCreatedDate());
  }
}
