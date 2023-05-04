package app.openschool.course.discussion.mapper;

import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.user.api.mapper.UserMapper;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class QuestionMapper {

  private QuestionMapper(){}

  public static Page<QuestionResponseDto> toQuestionDtoPage(Page<PeersQuestion> questionPage) {
    var questionResponseDtoList =
        questionPage.stream().map(QuestionMapper::toResponseDto).collect(Collectors.toList());
    return new PageImpl<>(questionResponseDtoList);
  }

  public static QuestionResponseDto toResponseDto(PeersQuestion peersQuestion) {
    return new QuestionResponseDto(
        peersQuestion.getId(),
        peersQuestion.getText(),
        UserMapper.toUserDto(peersQuestion.getUser()),
        CourseMapper.toCourseDto(peersQuestion.getCourse()),
        peersQuestion.getCreatedDate());
  }
}
