package app.openschool.course.discussion.util;

import app.openschool.common.exceptionhandler.exception.InvalidSearchQueryException;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.EnrolledCourseRepository;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

@Component
public class ValidationHandler {

  private static final int SEARCH_QUERY_MIN_LENGTH = 3;

  private MessageSource messageSource;

  private final EnrolledCourseRepository enrolledCourseRepository;

  public ValidationHandler(
      MessageSource messageSource, EnrolledCourseRepository enrolledCourseRepository) {
    this.messageSource = messageSource;
    this.enrolledCourseRepository = enrolledCourseRepository;
  }

  public void validateSearchQuery(String searchQuery) {
    if (Objects.nonNull(searchQuery) && searchQuery.length() < SEARCH_QUERY_MIN_LENGTH) {
      throw new InvalidSearchQueryException(
          messageSource.getMessage("discussion.mentor.question.query.size", null, Locale.ROOT));
    }
  }

  public boolean checkUserEnrollment(Long enrolledCourseId, String userEmail) {
    Optional<EnrolledCourse> enrolledCourse = enrolledCourseRepository.findById(enrolledCourseId);
    return enrolledCourse.isPresent()
        && enrolledCourse.get().getUser().getEmail().equals(userEmail);
  }
}
