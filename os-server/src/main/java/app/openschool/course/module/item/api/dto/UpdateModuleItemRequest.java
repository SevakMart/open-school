package app.openschool.course.module.item.api.dto;

import app.openschool.course.module.item.api.dto.basedto.ModuleItemRequestDto;

public class UpdateModuleItemRequest extends ModuleItemRequestDto {

  public UpdateModuleItemRequest() {}

  public UpdateModuleItemRequest(
      String title, Long moduleItemTypeId, String link, Long estimatedTime) {
    super(title, moduleItemTypeId, link, estimatedTime);
  }
}
