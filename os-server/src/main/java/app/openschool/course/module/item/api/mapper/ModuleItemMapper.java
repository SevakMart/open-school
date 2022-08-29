package app.openschool.course.module.item.api.mapper;

import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.api.dto.ModuleItemDto;

public class ModuleItemMapper {

  public static ModuleItemDto toModuleItemDto(ModuleItem moduleItem) {
    return new ModuleItemDto(
        moduleItem.getId(),
        moduleItem.getTitle(),
        moduleItem.getModuleItemType().getId(),
        moduleItem.getLink(),
        moduleItem.getEstimatedTime());
  }
}
