package app.openschool.course.module;

import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.module.api.dto.CreateModuleRequest;
import app.openschool.course.module.api.dto.UpdateModuleRequest;
import app.openschool.course.module.item.api.mapper.ModuleItemMapper;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.util.Locale;
import java.util.Set;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;


@Service
public class ModuleServiceImpl implements ModuleService {

  private final CourseRepository courseRepository;
  private final ModuleRepository moduleRepository;
  private final UserRepository userRepository;
  private final MessageSource messageSource;


  public ModuleServiceImpl(
          CourseRepository courseRepository,
          ModuleRepository moduleRepository,
          UserRepository userRepository,
          MessageSource messageSource, ModuleItemMapper moduleItemMapper) {
    this.courseRepository = courseRepository;
    this.moduleRepository = moduleRepository;
    this.userRepository = userRepository;
    this.messageSource = messageSource;
  }

  @Override
  public Module add(CreateModuleRequest request) {
    Course course =
        courseRepository.findById(request.getCourseId()).orElseThrow(IllegalArgumentException::new);
    Module module = new Module();
    module.setTitle(request.getTitle());
    module.setDescription(request.getDescription());
    module.setCourse(course);
    module.setModuleItems(ModuleItemMapper.toModuleItem(request.getCreateModuleItemRequests(),
            module));
    return moduleRepository.save(module);
  }

  @Override
  public Module update(Long moduleId, UpdateModuleRequest request) {
    Module module = moduleRepository.findById(moduleId).orElseThrow(IllegalArgumentException::new);
    User authenticatedUser =
        userRepository.findUserByEmail(
            SecurityContextHolder.getContext().getAuthentication().getName());
    if (authenticatedUser.getRole().getType().equals("MENTOR")
        && !module.getCourse().getMentor().getEmail().equals(authenticatedUser.getEmail())) {
      throw new IllegalArgumentException();
    }
    module.setTitle(request.getTitle());
    module.setDescription(request.getDescription());
    return moduleRepository.save(module);
  }

  @Override
  public void delete(Long moduleId) {
    Module module = moduleRepository.findById(moduleId).orElseThrow(IllegalArgumentException::new);
    User authenticatedUser =
        userRepository.findUserByEmail(
            SecurityContextHolder.getContext().getAuthentication().getName());
    if (authenticatedUser.getRole().getType().equals("MENTOR")
        && !module.getCourse().getMentor().getEmail().equals(authenticatedUser.getEmail())) {
      throw new IllegalArgumentException();
    }
    Set<Module> modules = module.getCourse().getModules();
    if (modules.size() == 1) {
      throw new UnsupportedOperationException(
          messageSource.getMessage("module.delete.not.allowed", null, Locale.ROOT));
    }
    modules.remove(module);
    moduleRepository.delete(module);
  }
}
