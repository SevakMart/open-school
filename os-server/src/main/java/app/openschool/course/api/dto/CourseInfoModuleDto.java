package app.openschool.course.api.dto;

import app.openschool.course.module.quiz.api.dto.QuizDto;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class CourseInfoModuleDto {
  @Schema(description = "Module title", example = "Collection")
  private final String title;

  @Schema(description = "Module description", example = "Deep understanding of data structures")
  private final String description;

  @ArraySchema(schema = @Schema(implementation = CourseInfoModuleItemDto.class))
  private final Set<CourseInfoModuleItemDto> moduleItemSet;

  @ArraySchema(schema = @Schema(implementation = QuizDto.class))
  private final Set<QuizDto> moduleQuizSet;

  public CourseInfoModuleDto(
      String title,
      String description,
      Set<CourseInfoModuleItemDto> moduleItemSet,
      Set<QuizDto> moduleQuizSet) {
    this.title = title;
    this.description = description;
    this.moduleItemSet = moduleItemSet;
    this.moduleQuizSet = moduleQuizSet;
  }

  public String getTitle() {
    return title;
  }

  public Set<CourseInfoModuleItemDto> getModuleItemSet() {
    return moduleItemSet;
  }

  public String getDescription() {
    return description;
  }

  public Set<QuizDto> getModuleQuizSet() {
    return moduleQuizSet;
  }
}
