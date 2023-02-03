package app.openschool.discussion.api;

import app.openschool.discussion.api.dto.DiscussionAnswerRequestDto;
import app.openschool.discussion.api.dto.DiscussionAnswerResponseDto;

import java.security.Principal;

public interface DiscussionAnswerService {

  DiscussionAnswerResponseDto create(DiscussionAnswerRequestDto requestDto, Principal principal);

  DiscussionAnswerResponseDto update(Long answerId, DiscussionAnswerRequestDto requestDto);

  void delete(Long id);
}
