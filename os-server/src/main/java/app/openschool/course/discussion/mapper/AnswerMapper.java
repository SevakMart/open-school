package app.openschool.course.discussion.mapper;

import app.openschool.course.discussion.Answer;
import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.user.api.mapper.UserMapper;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class AnswerMapper {

  private AnswerMapper() {}

  public static Page<AnswerResponseDto> toAnswerDtoPage(Page<? extends Answer> answerPage) {
    var answerResponseDtoList =
        answerPage.stream().map(AnswerMapper::toAnswerDto).collect(Collectors.toList());
    return new PageImpl<>(answerResponseDtoList);
  }

  public static AnswerResponseDto toAnswerDto(Answer answer) {
    AnswerResponseDto answerResponseDto = new AnswerResponseDto();
    answerResponseDto.setId(answer.getId());
    answerResponseDto.setText(answer.getText());
    answerResponseDto.setUserDto(UserMapper.toUserDto(answer.getUser()));
    answerResponseDto.setCreatedDate(answer.getCreatedDate());
    return answerResponseDto;
  }
}
