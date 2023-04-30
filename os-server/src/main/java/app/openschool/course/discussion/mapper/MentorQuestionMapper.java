package app.openschool.course.discussion.mapper;

import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.course.discussion.dto.MentorQuestionResponseDto;
import app.openschool.course.discussion.dto.basedto.IQuestion;
import app.openschool.course.discussion.mentor.question.MentorQuestion;
import app.openschool.user.api.mapper.MentorMapper;
import app.openschool.user.api.mapper.UserMapper;import org.springframework.data.domain.Page;import org.springframework.data.domain.PageImpl;import java.util.stream.Collectors;

public class MentorQuestionMapper {

  private MentorQuestionMapper() {}

//    public static MentorQuestionResponseDto toResponseDto(MentorQuestion iQuestion) {
  public static MentorQuestionResponseDto toResponseDto(IQuestion iQuestion) {
    return new MentorQuestionResponseDto(
        iQuestion.getId(),
        iQuestion.getText(),
        MentorMapper.toMentorDto(iQuestion.getCourse().getMentor()),
        UserMapper.toUserDto(iQuestion.getUser()),
        CourseMapper.toCourseDto(iQuestion.getCourse()),
        iQuestion.getCreatedDate());
  }

  public static Page<MentorQuestionResponseDto> toQuestionDtoPage(Page<MentorQuestion> questionPage) {
    var questionResponseDtoList =
            questionPage.stream().map(MentorQuestionMapper::toResponseDto).collect(Collectors.toList());
    return new PageImpl<>(questionResponseDtoList);
  }
}
