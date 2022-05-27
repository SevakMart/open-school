package app.openschool.course.api.dto;

public class CourseInfoModuleItemDto {

  private String moduleItemType;
  private String link;

  public CourseInfoModuleItemDto(String moduleItemType, String link) {
    this.moduleItemType = moduleItemType;
    this.link = link;
  }

  public String getModuleItemType() {
    return moduleItemType;
  }

  public void setModuleItemType(String moduleItemType) {
    this.moduleItemType = moduleItemType;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }
}
