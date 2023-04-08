package app.openschool.course.discussion.mapper;

import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.course.discussion.peers.answer.PeersAnswer;
import app.openschool.user.api.mapper.UserMapper;

public class AnswerMapper {

  private AnswerMapper(){}

  public static AnswerResponseDto toAnswerDto(PeersAnswer peersAnswer) {
    AnswerResponseDto answerResponseDto = new AnswerResponseDto();
    answerResponseDto.setId(peersAnswer.getId());
    answerResponseDto.setText(peersAnswer.getText());
    answerResponseDto.setUserDto(UserMapper.toUserDto(peersAnswer.getUser()));
    answerResponseDto.setCreatedDate(peersAnswer.getCreatedDate());
    return answerResponseDto;
  }
}
