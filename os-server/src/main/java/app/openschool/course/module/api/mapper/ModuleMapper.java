package app.openschool.course.module.api.mapper;

import app.openschool.course.Course;
import app.openschool.course.module.Module;
import app.openschool.course.module.ModuleRepository;
import app.openschool.course.module.api.dto.CreateModuleRequest;
import app.openschool.course.module.api.dto.ModuleDto;
import app.openschool.course.module.item.api.mapper.ModuleItemMapper;
import java.util.Set;
import java.util.stream.Collectors;




public class ModuleMapper {
  private final ModuleItemMapper moduleItemMapper;
  private final ModuleRepository moduleRepository;

  public ModuleMapper(ModuleItemMapper moduleItemMapper, ModuleRepository moduleRepository) {
    this.moduleItemMapper = moduleItemMapper;
    this.moduleRepository = moduleRepository;
  }

  public static ModuleDto toModuleDto(Module module) {
    return new ModuleDto(
        module.getId(), module.getTitle(),
        module.getDescription(), module.getCourse().getId());
  }

  public static Set<Module> toModules(Set<CreateModuleRequest> requests, Course course) {
    Set<Module> modules =
        requests.stream()
            .map(
                x -> {
                  Module module = new Module();
                  module.setTitle(x.getTitle());
                  module.setDescription(x.getDescription());
                  module.setCourse(course);
                  module.setModuleItems(
                      ModuleItemMapper.toModuleItem(x.getCreateModuleItemRequests(), module));
                  return module;
                })
            .collect(Collectors.toSet());
    Set<Module> modules1 = course.getModules();
    modules1.addAll(modules);
    return modules1;
  }
}
