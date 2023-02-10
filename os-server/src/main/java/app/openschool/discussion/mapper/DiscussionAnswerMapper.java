package app.openschool.discussion.mapper;

import app.openschool.discussion.DiscussionAnswer;
import app.openschool.discussion.dto.DiscussionAnswerResponseDto;
import app.openschool.user.api.mapper.UserMapper;

public class DiscussionAnswerMapper {

  public static DiscussionAnswerResponseDto toAnswerDto(DiscussionAnswer discussionAnswer) {
    DiscussionAnswerResponseDto discussionAnswerResponseDto = new DiscussionAnswerResponseDto();
    discussionAnswerResponseDto.setId(discussionAnswer.getId());
    discussionAnswerResponseDto.setText(discussionAnswer.getText());
    discussionAnswerResponseDto.setUserDto(UserMapper.toUserDto(discussionAnswer.getUser()));
    discussionAnswerResponseDto.setCreatedDate(discussionAnswer.getCreatedDate());
    return discussionAnswerResponseDto;
  }
}
