package app.openschool.discussion.api;

import app.openschool.discussion.api.dto.DiscussionQuestionRequestDto;
import app.openschool.discussion.api.dto.DiscussionQuestionResponseDto;
import java.security.Principal;

public interface DiscussionQuestionService {

  DiscussionQuestionResponseDto findById(Long id);

  //  Page<DiscussionQuestionResponseDto> findQuestionByCourseId(Long id, Pageable pageable);

  DiscussionQuestionResponseDto create(
      DiscussionQuestionRequestDto requestDto, Principal principal);

  DiscussionQuestionResponseDto update(Long id, DiscussionQuestionRequestDto requestDto);

  void delete(Long id);
}
