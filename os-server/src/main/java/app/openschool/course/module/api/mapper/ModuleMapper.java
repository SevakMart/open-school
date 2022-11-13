package app.openschool.course.module.api.mapper;

import app.openschool.course.Course;
import app.openschool.course.module.Module;
import app.openschool.course.module.api.dto.CreateModuleRequest;
import app.openschool.course.module.api.dto.ModuleDto;
import app.openschool.course.module.item.api.mapper.ModuleItemMapper;
import java.util.Set;
import java.util.stream.Collectors;

public class ModuleMapper {

  public static ModuleDto toModuleDto(Module module) {
    return new ModuleDto(
        module.getId(), module.getTitle(),
        module.getDescription(), module.getCourse().getId());
  }

  public static Set<Module> toModules(Set<CreateModuleRequest> requests, Course course) {
    Set<Module> requestModules =
        requests.stream()
            .map(
                moduleRequest -> {
                  Module module = new Module();
                  module.setTitle(moduleRequest.getTitle());
                  module.setDescription(moduleRequest.getDescription());
                  module.setCourse(course);
                  module.setModuleItems(
                      ModuleItemMapper.toModuleItem(
                          moduleRequest.getCreateModuleItemRequests(), module));
                  return module;
                })
            .collect(Collectors.toSet());
    Set<Module> modules = course.getModules();
    modules.addAll(requestModules);
    return modules;
  }
}
