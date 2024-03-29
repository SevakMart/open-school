package app.openschool.course.module;

import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.module.api.dto.CreateModuleRequest;
import app.openschool.course.module.api.dto.UpdateModuleRequest;
import app.openschool.course.module.item.api.mapper.ModuleItemMapper;
import java.util.Locale;
import java.util.Set;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {

  private final CourseRepository courseRepository;
  private final ModuleRepository moduleRepository;
  private final MessageSource messageSource;

  public ModuleServiceImpl(
      CourseRepository courseRepository,
      ModuleRepository moduleRepository,
      MessageSource messageSource) {
    this.courseRepository = courseRepository;
    this.moduleRepository = moduleRepository;
    this.messageSource = messageSource;
  }

  @Override
  public Module findModuleById(Long moduleId) {
    return moduleRepository.findById(moduleId).orElseThrow(IllegalArgumentException::new);
  }

  @Override
  public Module add(CreateModuleRequest request) {
    Course course =
        courseRepository.findById(request.getCourseId()).orElseThrow(IllegalArgumentException::new);
    Module module = new Module();
    module.setTitle(request.getTitle());
    module.setDescription(request.getDescription());
    module.setCourse(course);
    module.setModuleItems(
        ModuleItemMapper.toModuleItem(request.getCreateModuleItemRequests(), module));
    return moduleRepository.save(module);
  }

  @Override
  public Module update(Long moduleId, UpdateModuleRequest request) {
    Module module = moduleRepository.findById(moduleId).orElseThrow(IllegalArgumentException::new);
    module.setTitle(request.getTitle());
    module.setDescription(request.getDescription());
    return moduleRepository.save(module);
  }

  @Override
  public void delete(Long moduleId) {
    Module module = moduleRepository.findById(moduleId).orElseThrow(IllegalArgumentException::new);
    Set<Module> modules = module.getCourse().getModules();
    if (modules.size() == 1) {
      throw new UnsupportedOperationException(
          messageSource.getMessage("module.delete.not.allowed", null, Locale.ROOT));
    }
    modules.remove(module);
    moduleRepository.delete(module);
  }
}
