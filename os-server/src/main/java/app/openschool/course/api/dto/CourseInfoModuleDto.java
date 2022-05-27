package app.openschool.course.api.dto;

import java.util.Set;

public class CourseInfoModuleDto {

  private final String title;
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
