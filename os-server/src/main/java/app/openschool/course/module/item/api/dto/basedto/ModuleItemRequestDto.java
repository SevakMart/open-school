package app.openschool.course.module.item.api.dto.basedto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;

public abstract class ModuleItemRequestDto {

  @NotBlank(message = "{argument.required}")
  @Length(max = 45, message = "{length.max.string}")
  private String title;

  @NotNull(message = "{argument.required}")
  private Long moduleItemTypeId;

  @NotBlank(message = "{argument.required}")
  private String link;

  @NotNull(message = "{argument.required}")
  private Long estimatedTime;

  public ModuleItemRequestDto() {}

  public ModuleItemRequestDto(
      String title, Long moduleItemTypeId, String link, Long estimatedTime) {
    this.title = title;
    this.moduleItemTypeId = moduleItemTypeId;
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
    return moduleItemTypeId;
  }

  public void setModuleItemTypeId(Long moduleItemTypeId) {
    this.moduleItemTypeId = moduleItemTypeId;
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
