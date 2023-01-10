package app.openschool.course.module;

import app.openschool.course.module.api.dto.CreateModuleRequest;
import app.openschool.course.module.api.dto.UpdateModuleRequest;

public interface ModuleService {
  Module findModuleById(Long moduleId);

  Module add(CreateModuleRequest request);

  Module update(Long moduleId, UpdateModuleRequest request);

  void delete(Long moduleId);
}
