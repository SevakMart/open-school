package app.openschool.course.module.api.mapper;

import app.openschool.course.module.Module;
import app.openschool.course.module.api.dto.ModuleDto;

public class ModuleMapper {

  public static ModuleDto toModuleDto(Module module) {
    return new ModuleDto(
        module.getId(), module.getTitle(), module.getDescription(), module.getCourse().getId());
  }
}
