package app.openschool.course.api.dto;

import java.util.Set;

public class CourseInfoModuleDto {

  private String title;
  private Set<CourseInfoModuleItemDto> moduleItemSet;

  public CourseInfoModuleDto(String title, Set<CourseInfoModuleItemDto> moduleItemSet) {
    this.title = title;
    this.moduleItemSet = moduleItemSet;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Set<CourseInfoModuleItemDto> getModuleItemSet() {
    return moduleItemSet;
  }

  public void setModuleItemSet(Set<CourseInfoModuleItemDto> moduleItemSet) {
    this.moduleItemSet = moduleItemSet;
  }
}
