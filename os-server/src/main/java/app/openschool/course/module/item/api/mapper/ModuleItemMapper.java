package app.openschool.course.module.item.api.mapper;

import app.openschool.course.module.Module;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.api.dto.CreateModuleItemRequest;
import app.openschool.course.module.item.api.dto.ModuleItemDto;
import app.openschool.course.module.item.type.ModuleItemType;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;


@Component
public class ModuleItemMapper {



  public ModuleItemDto toModuleItemDto(ModuleItem moduleItem) {
    return new ModuleItemDto(
        moduleItem.getId(),
        moduleItem.getTitle(),
        moduleItem.getModuleItemType().getId(),
        moduleItem.getLink(),
        moduleItem.getEstimatedTime());
  }

  public static Set<ModuleItem> toModuleItem(
      Set<CreateModuleItemRequest> createModuleItemRequests, Module module) {
    Set<ModuleItem> collect =
        createModuleItemRequests.stream()
            .map(
                x -> {
                  ModuleItem moduleItem = new ModuleItem();
                  moduleItem.setTitle(x.getTitle());
                  moduleItem.setModule(module);
                  moduleItem.setEstimatedTime(x.getEstimatedTime());
                  moduleItem.setLink(x.getLink());
                  ModuleItemType moduleItemType = new ModuleItemType();
                  moduleItemType.setId(x.getModuleItemTypeId());
                  moduleItem.setModuleItemType(moduleItemType);
                  return moduleItem;
                })
            .collect(Collectors.toSet());
    Set<ModuleItem> moduleItems = module.getModuleItems();
    moduleItems.addAll(collect);
    return moduleItems;
  }
}
