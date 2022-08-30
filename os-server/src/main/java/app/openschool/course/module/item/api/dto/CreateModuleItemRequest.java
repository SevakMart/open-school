package app.openschool.course.module.item.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public class CreateModuleItemRequest {

  @NotNull(message = "{argument.required}")
  private Long moduleId;

  @NotBlank(message = "{argument.required}")
  @Length(max = 45, message = "{length.max.string}")
  private String title;

  @NotNull(message = "{argument.required}")
  private Long ModuleItemTypeId;

  @NotBlank(message = "{argument.required}")
  private String link;

  @NotNull(message = "{argument.required}")
  private Long estimatedTime;

  public CreateModuleItemRequest() {}

  public CreateModuleItemRequest(
      Long moduleId, String title, Long moduleItemTypeId, String link, Long estimatedTime) {
    this.moduleId = moduleId;
    this.title = title;
    ModuleItemTypeId = moduleItemTypeId;
    this.link = link;
    this.estimatedTime = estimatedTime;
  }

  public Long getModuleId() {
    return moduleId;
  }

  public void setModuleId(Long moduleId) {
    this.moduleId = moduleId;
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
