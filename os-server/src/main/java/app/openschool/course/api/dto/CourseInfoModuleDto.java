package app.openschool.course.api.dto;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;

public class CourseInfoModuleDto {
  @Schema(description = "Module title", example = "Collection")
  private final String title;

  @ArraySchema(schema = @Schema(implementation = CourseInfoModuleItemDto.class))
  private final Set<CourseInfoModuleItemDto> moduleItemSet;

  public CourseInfoModuleDto(String title, Set<CourseInfoModuleItemDto> moduleItemSet) {
    this.title = title;
    this.moduleItemSet = moduleItemSet;
  }

  public String getTitle() {
    return title;
  }

  public Set<CourseInfoModuleItemDto> getModuleItemSet() {
    return moduleItemSet;
  }
}
