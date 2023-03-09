package app.openschool.course.module.item.api.dto;

import app.openschool.course.module.item.api.dto.basedto.ModuleItemRequestDto;
import javax.validation.constraints.NotNull;

public class CreateModuleItemRequest extends ModuleItemRequestDto {

  @NotNull(message = "{argument.required}")
  private Long moduleId;

  public CreateModuleItemRequest() {}

  public CreateModuleItemRequest(
      Long moduleId, String title, Long moduleItemTypeId, String link, Long estimatedTime) {
    super(title, moduleItemTypeId, link, estimatedTime);
    this.moduleId = moduleId;
  }

  public Long getModuleId() {
    return moduleId;
  }

  public void setModuleId(Long moduleId) {
    this.moduleId = moduleId;
  }
}
