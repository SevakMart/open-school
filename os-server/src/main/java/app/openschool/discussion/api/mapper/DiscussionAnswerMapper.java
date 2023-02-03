package app.openschool.discussion.api.mapper;

import app.openschool.discussion.api.DiscussionAnswer;
import app.openschool.discussion.api.dto.DiscussionAnswerRequestDto;
import app.openschool.discussion.api.dto.DiscussionAnswerResponseDto;
import app.openschool.user.api.mapper.UserMapper;

public class DiscussionAnswerMapper {

  public static DiscussionAnswer toAnswer(DiscussionAnswerRequestDto requestDto) {
    DiscussionAnswer discussionAnswer = new DiscussionAnswer();
    discussionAnswer.setText(requestDto.getText());
    //    discussionAnswer.setUser(requestDto.getUser());
    return discussionAnswer;
  }

  public static DiscussionAnswerResponseDto toAnswerDto(DiscussionAnswer discussionAnswer) {
    DiscussionAnswerResponseDto discussionAnswerResponseDto = new DiscussionAnswerResponseDto();
    discussionAnswerResponseDto.setId(discussionAnswer.getId());
    discussionAnswerResponseDto.setText(discussionAnswer.getText());
    discussionAnswerResponseDto.setUserDto(UserMapper.toUserDto(discussionAnswer.getUser()));
    discussionAnswerResponseDto.setCreatedDate(discussionAnswer.getCreatedDate());
    return discussionAnswerResponseDto;
  }
}
