package app.openschool.course.module.item;

import app.openschool.course.module.item.api.dto.CreateModuleItemRequest;
import app.openschool.course.module.item.api.dto.UpdateModuleItemRequest;

public interface ModuleItemService {

  ModuleItem findByModuleItemId(Long moduleItemId);

  ModuleItem add(CreateModuleItemRequest request);

  ModuleItem update(Long moduleItemId, UpdateModuleItemRequest request);

  void delete(Long moduleItemId);
}
