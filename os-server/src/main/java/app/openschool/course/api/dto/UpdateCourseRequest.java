package app.openschool.course.api.dto;

import app.openschool.course.api.dto.basedto.CourseRequestDto;
import java.util.Set;

public class UpdateCourseRequest extends CourseRequestDto {

  public UpdateCourseRequest() {}

  public UpdateCourseRequest(
      String title,
      String description,
      String goal,
      Long categoryId,
      Integer difficultyId,
      Integer languageId,
      Set<Long> keywordIds) {
    super(title, description, goal, categoryId, difficultyId, languageId, keywordIds);
  }
}
