package app.openschool.course.module.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ModuleDto {

  @Schema(description = "Module Id", example = "1")
  private final Long id;

  @Schema(description = "Course title", example = "Multithreading")
  private final String title;

  @Schema(description = "Module description", example = "Is planned to improve skills")
  private final String description;

  @Schema(description = "Id of the course which the module belongs")
  private final Long courseId;

  public ModuleDto(Long id, String title, String description, Long courseId) {
    this.id = id;
    this.title = title;
    this.description = description;
    this.courseId = courseId;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Long getCourseId() {
    return courseId;
  }
}
