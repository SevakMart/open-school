package app.openschool.course.discussion.util;

import app.openschool.common.exceptionhandler.exception.InvalidSearchQueryException;
import java.util.Locale;
import java.util.Objects;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ValidationHandler {

  private static final int SEARCH_QUERY_MIN_LENGTH = 3;

  private MessageSource messageSource;

  public ValidationHandler(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public void validateSearchQuery(String searchQuery) {
    if (Objects.nonNull(searchQuery) && searchQuery.length() < SEARCH_QUERY_MIN_LENGTH) {
      throw new InvalidSearchQueryException(
          messageSource.getMessage("discussion.mentor.question.query.size", null, Locale.ROOT));
    }
  }
}
