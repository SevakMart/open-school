package app.openschool.course.module;

import app.openschool.course.Course;
import app.openschool.course.CourseRepository;
import app.openschool.course.module.api.dto.CreateModuleRequest;
import app.openschool.course.module.api.dto.UpdateModuleRequest;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.api.dto.CreateModuleItemRequest;
import app.openschool.course.module.item.type.ModuleItemTypeRepository;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ModuleServiceImpl implements ModuleService {

  private final CourseRepository courseRepository;
  private final ModuleRepository moduleRepository;
  private final UserRepository userRepository;
  private final ModuleItemTypeRepository moduleItemTypeRepository;
  private final MessageSource messageSource;

  public ModuleServiceImpl(
      CourseRepository courseRepository,
      ModuleRepository moduleRepository,
      UserRepository userRepository,
      ModuleItemTypeRepository moduleItemTypeRepository,
      MessageSource messageSource) {
    this.courseRepository = courseRepository;
    this.moduleRepository = moduleRepository;
    this.userRepository = userRepository;
    this.moduleItemTypeRepository = moduleItemTypeRepository;
    this.messageSource = messageSource;
  }

  @Override
  public Module add(CreateModuleRequest request) {
    Course course =
        courseRepository.findById(request.getCourseId()).orElseThrow(IllegalArgumentException::new);
    User authenticatedUser =
        userRepository.findUserByEmail(
            SecurityContextHolder.getContext().getAuthentication().getName());
    if (authenticatedUser.getRole().getType().equals("MENTOR")
        && !course.getMentor().getEmail().equals(authenticatedUser.getEmail())) {
      throw new IllegalArgumentException();
    }
    Module module = new Module();
    module.setTitle(request.getTitle());
    module.setDescription(request.getDescription());
    module.setCourse(course);
    module.setModuleItems(createModuleItems(request.getCreateModuleItemRequests(), module));
    return moduleRepository.save(module);
  }

  private Set<ModuleItem> createModuleItems(Set<CreateModuleItemRequest> requests, Module module) {
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
