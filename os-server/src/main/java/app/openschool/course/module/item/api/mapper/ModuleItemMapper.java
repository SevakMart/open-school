package app.openschool.course.module.item.api.mapper;

import app.openschool.course.module.Module;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.api.dto.CreateModuleItemRequest;
import app.openschool.course.module.item.api.dto.ModuleItemDto;
import app.openschool.course.module.item.type.ModuleItemType;
import app.openschool.course.module.item.type.ModuleItemTypeRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;


@Component
public class ModuleItemMapper {

  private final ModuleItemTypeRepository moduleItemTypeRepository;

  public ModuleItemMapper(ModuleItemTypeRepository moduleItemTypeRepository) {
    this.moduleItemTypeRepository = moduleItemTypeRepository;
  }

  public ModuleItemDto toModuleItemDto(ModuleItem moduleItem) {
    return new ModuleItemDto(
                moduleItem.getId(),
                moduleItem.getTitle(),
                moduleItem.getModuleItemType().getId(),
                moduleItem.getLink(),
                moduleItem.getEstimatedTime());
  }

  public Set<ModuleItem> toModuleItems(Set<CreateModuleItemRequest> requests, Module module) {
    Set<ModuleItem> createdModuleItems =
                requests.stream()
                        .map(
                                createModuleItemRequest -> {
                            ModuleItem moduleItem = new ModuleItem();
                            moduleItem.setTitle(createModuleItemRequest.getTitle());
                            moduleItem.setEstimatedTime(createModuleItemRequest.getEstimatedTime());
                            moduleItem.setLink(createModuleItemRequest.getLink());
                            moduleItem.setModuleItemType(
                                            moduleItemTypeRepository
                                            .findById(createModuleItemRequest.getModuleItemTypeId())
                                            .orElseThrow(IllegalArgumentException::new));
                            moduleItem.setModule(module);
                            return moduleItem;
                                })
                            .collect(Collectors.toSet());
    Set<ModuleItem> moduleItems = module.getModuleItems();
    moduleItems.addAll(createdModuleItems);
    return moduleItems;
  }




}
