package app.openschool.discussion;

import app.openschool.discussion.dto.DiscussionQuestionRequestDto;
import app.openschool.discussion.dto.DiscussionQuestionResponseDto;
import java.security.Principal;

public interface DiscussionQuestionService {
  DiscussionQuestionResponseDto create(
      DiscussionQuestionRequestDto requestDto, Principal principal);
}
