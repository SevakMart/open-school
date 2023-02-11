package app.openschool.discussion;

import app.openschool.discussion.dto.DiscussionAnswerRequestDto;
import app.openschool.discussion.dto.DiscussionAnswerResponseDto;
import java.security.Principal;

public interface DiscussionAnswerService {

  DiscussionAnswerResponseDto create(DiscussionAnswerRequestDto requestDto, Principal principal);
}
