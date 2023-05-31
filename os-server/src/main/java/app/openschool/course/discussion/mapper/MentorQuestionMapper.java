package app.openschool.course.discussion.mapper;

import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.discussion.Question;
import app.openschool.course.discussion.dto.MentorQuestionResponseDto;
import app.openschool.user.api.mapper.UserMapper;
import java.util.stream.Collectors;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class MentorQuestionMapper {

  private MentorQuestionMapper() {}

  public static MentorQuestionResponseDto toResponseDto(Question question) {
    return new MentorQuestionResponseDto(
        question.getId(),
        question.getText(),
        UserMapper.toUserDto(question.getUser()),
        CourseMapper.toCourseDto(question.getCourse()),
        question.getCreatedDate());
  }

  public static Page<MentorQuestionResponseDto> toQuestionDtoPage(
      Page<? extends Question> questionPage) {
    var questionResponseDtoList =
        questionPage.stream().map(MentorQuestionMapper::toResponseDto).collect(Collectors.toList());
    return new PageImpl<>(questionResponseDtoList);
  }
}
