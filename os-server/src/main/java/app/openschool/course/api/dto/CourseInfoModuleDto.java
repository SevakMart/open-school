package app.openschool.course.api.dto;

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

  public CourseInfoModuleDto(
      String title, String description, Set<CourseInfoModuleItemDto> moduleItemSet) {
    this.title = title;
    this.description = description;
    this.moduleItemSet = moduleItemSet;
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
}
