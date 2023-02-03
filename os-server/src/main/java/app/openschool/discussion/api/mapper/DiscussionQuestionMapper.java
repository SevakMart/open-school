package app.openschool.discussion.api.mapper;

import app.openschool.course.api.mapper.CourseMapper;
import app.openschool.discussion.api.DiscussionQuestion;
import app.openschool.discussion.api.dto.DiscussionQuestionResponseDto;
import app.openschool.user.api.mapper.UserMapper;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public class DiscussionQuestionMapper {

  public static DiscussionQuestionResponseDto toResponseDto(DiscussionQuestion discussionQuestion) {
    DiscussionQuestionResponseDto discussionQuestionResponseDto =
        new DiscussionQuestionResponseDto();
    discussionQuestionResponseDto.setId(discussionQuestion.getId());
    discussionQuestionResponseDto.setText(discussionQuestion.getText());
    discussionQuestionResponseDto.setUserDto(UserMapper.toUserDto(discussionQuestion.getUser()));
    discussionQuestionResponseDto.setCourseDto(
        CourseMapper.toCourseDto(discussionQuestion.getCourse()));
    discussionQuestionResponseDto.setCreatedDate(discussionQuestion.getCreatedDate());
    return discussionQuestionResponseDto;
  }

  public static Page<DiscussionQuestionResponseDto> toDiscussionQuestionPageDto(
      Page<DiscussionQuestion> discussionQuestionPage) {
    List<DiscussionQuestion> discussionQuestions = discussionQuestionPage.toList();
    List<DiscussionQuestionResponseDto> discussionQuestionResponseDtoList = new ArrayList<>();
    for (DiscussionQuestion discussionQuestion : discussionQuestions) {
      discussionQuestionResponseDtoList.add(toResponseDto(discussionQuestion));
    }
    return new PageImpl<>(
        discussionQuestionResponseDtoList,
        discussionQuestionPage.getPageable(),
        discussionQuestionPage.getTotalElements());
  }
}
