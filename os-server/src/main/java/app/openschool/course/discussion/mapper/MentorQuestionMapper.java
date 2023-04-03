package app.openschool.course.discussion.mapper;

import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.discussion.dto.QuestionResponseDto;
import app.openschool.course.discussion.mentor.question.MentorQuestion;
import app.openschool.user.api.mapper.UserMapper;

public class MentorQuestionMapper {

  // ToDo this class and the components used in it will be changed in the future
  private MentorQuestionMapper(){}

  public static QuestionResponseDto toResponseDto(MentorQuestion mentorQuestion) {
    return new QuestionResponseDto(
        mentorQuestion.getId(),
        mentorQuestion.getText(),
        UserMapper.toUserDto(mentorQuestion.getUser()),
        CourseMapper.toCourseDto(mentorQuestion.getCourse()),
        mentorQuestion.getCreatedDate());
  }
}
