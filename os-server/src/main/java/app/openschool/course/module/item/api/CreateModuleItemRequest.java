package app.openschool.course.module.item.api;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CreateModuleItemRequest {

  @NotBlank(message = "")
  private String title;

  @NotNull private Long ModuleItemTypeId;

  @NotBlank private String link;

  @NotNull private Long estimatedTime;

  public CreateModuleItemRequest() {}

  public CreateModuleItemRequest(
      String title, Long moduleItemTypeId, String link, Long estimatedTime) {
    this.title = title;
    ModuleItemTypeId = moduleItemTypeId;
    this.link = link;
    this.estimatedTime = estimatedTime;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Long getModuleItemTypeId() {
    return ModuleItemTypeId;
  }

  public void setModuleItemTypeId(Long moduleItemTypeId) {
    ModuleItemTypeId = moduleItemTypeId;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public Long getEstimatedTime() {
    return estimatedTime;
  }

  public void setEstimatedTime(Long estimatedTime) {
    this.estimatedTime = estimatedTime;
  }
}
