package app.openschool.course.discussion.mapper;

import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.dto.basedto.IQuestion;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.user.api.mapper.UserMapper;

public class QuestionMapper {

  private QuestionMapper(){}

  //  public static QuestionResponseDto toResponseDto(PeersQuestion peersQuestion) {
  public static QuestionResponseDto toResponseDto(IQuestion peersQuestion) {
    return new QuestionResponseDto(
        peersQuestion.getId(),
        peersQuestion.getText(),
        UserMapper.toUserDto(peersQuestion.getUser()),
        CourseMapper.toCourseDto(peersQuestion.getCourse()),
        peersQuestion.getCreatedDate());
  }
}
