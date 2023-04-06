package app.openschool.course.discussion.mapper;

import app.openschool.course.discussion.dto.AnswerResponseDto;
import app.openschool.course.discussion.mentor.answer.MentorAnswer;
import app.openschool.user.api.mapper.UserMapper;

public class MentorAnswerMapper {

  // ToDo this class and the components used in it will be changed in the future
  private MentorAnswerMapper(){}

  public static AnswerResponseDto toAnswerDto(MentorAnswer mentorAnswer) {
    return new AnswerResponseDto(
        mentorAnswer.getId(),
        mentorAnswer.getText(),
        UserMapper.toUserDto(mentorAnswer.getUser()),
        mentorAnswer.getCreatedDate());
  }
}
