package app.openschool.course.discussion.mapper;

import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.course.discussion.peers.answer.PeersAnswer;
import app.openschool.user.api.mapper.UserMapper;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class AnswerMapper {

  private AnswerMapper() {}

  public static Page<AnswerResponseDto> toAnswerDtoPage(Page<PeersAnswer> answerPage) {
    var answerResponseDtoList =
        answerPage.stream().map(AnswerMapper::toAnswerDto).collect(Collectors.toList());
    return new PageImpl<>(answerResponseDtoList);
  }

  public static AnswerResponseDto toAnswerDto(PeersAnswer peersAnswer) {
    AnswerResponseDto answerResponseDto = new AnswerResponseDto();
    answerResponseDto.setId(peersAnswer.getId());
    answerResponseDto.setText(peersAnswer.getText());
    answerResponseDto.setUserDto(UserMapper.toUserDto(peersAnswer.getUser()));
    answerResponseDto.setCreatedDate(peersAnswer.getCreatedDate());
    return answerResponseDto;
  }
}
