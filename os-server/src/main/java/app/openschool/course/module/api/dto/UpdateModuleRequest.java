package app.openschool.course.module.api.dto;

import app.openschool.course.module.api.dto.basedto.ModuleRequestDto;

public class UpdateModuleRequest extends ModuleRequestDto {
  public UpdateModuleRequest() {}

  public UpdateModuleRequest(String title, String description) {
    super(title, description);
  }

}
