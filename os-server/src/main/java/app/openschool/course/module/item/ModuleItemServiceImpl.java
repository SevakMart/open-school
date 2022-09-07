package app.openschool.course.module.item;

import app.openschool.course.module.Module;
import app.openschool.course.module.ModuleRepository;
import app.openschool.course.module.item.api.dto.CreateModuleItemRequest;
import app.openschool.course.module.item.api.dto.UpdateModuleItemRequest;
import app.openschool.course.module.item.type.ModuleItemTypeRepository;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import java.util.Locale;
import java.util.Set;
import org.springframework.context.MessageSource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ModuleItemServiceImpl implements ModuleItemService {

  private final ModuleRepository moduleRepository;
  private final ModuleItemRepository moduleItemRepository;
  private final UserRepository userRepository;
  private final ModuleItemTypeRepository moduleItemTypeRepository;
  private final MessageSource messageSource;

  public ModuleItemServiceImpl(
      ModuleRepository moduleRepository,
      ModuleItemRepository moduleItemRepository,
      UserRepository userRepository,
      ModuleItemTypeRepository moduleItemTypeRepository,
      MessageSource messageSource) {
    this.moduleRepository = moduleRepository;
    this.moduleItemRepository = moduleItemRepository;
    this.userRepository = userRepository;
    this.moduleItemTypeRepository = moduleItemTypeRepository;
    this.messageSource = messageSource;
  }

  @Override
  public ModuleItem add(CreateModuleItemRequest request) {
    Module module =
        moduleRepository.findById(request.getModuleId()).orElseThrow(IllegalArgumentException::new);
    User authenticatedUser =
        userRepository.findUserByEmail(
            SecurityContextHolder.getContext().getAuthentication().getName());
    if (authenticatedUser.getRole().getType().equals("MENTOR")
        && !module.getCourse().getMentor().getEmail().equals(authenticatedUser.getEmail())) {
      throw new IllegalArgumentException();
    }
    ModuleItem moduleItem = new ModuleItem();
    moduleItem.setTitle(request.getTitle());
    moduleItem.setEstimatedTime(request.getEstimatedTime());
    moduleItem.setLink(request.getLink());
    moduleItem.setModuleItemType(
        moduleItemTypeRepository
            .findById(request.getModuleItemTypeId())
            .orElseThrow(IllegalArgumentException::new));
    moduleItem.setModule(module);
    return moduleItemRepository.save(moduleItem);
  }

  @Override
  public ModuleItem update(Long moduleItemId, UpdateModuleItemRequest request) {
    ModuleItem moduleItem =
        moduleItemRepository.findById(moduleItemId).orElseThrow(IllegalArgumentException::new);
    User authenticatedUser =
        userRepository.findUserByEmail(
            SecurityContextHolder.getContext().getAuthentication().getName());
    if (authenticatedUser.getRole().getType().equals("MENTOR")
        && !moduleItem
            .getModule()
            .getCourse()
            .getMentor()
            .getEmail()
            .equals(authenticatedUser.getEmail())) {
      throw new IllegalArgumentException();
    }
    moduleItem.setTitle(request.getTitle());
    moduleItem.setEstimatedTime(request.getEstimatedTime());
    moduleItem.setLink(request.getLink());
    moduleItem.setModuleItemType(
        moduleItemTypeRepository
            .findById(request.getModuleItemTypeId())
            .orElseThrow(IllegalArgumentException::new));
    return moduleItemRepository.save(moduleItem);
  }

  @Override
  public void delete(Long moduleItemId) {
    ModuleItem moduleItem =
        moduleItemRepository.findById(moduleItemId).orElseThrow(IllegalArgumentException::new);
    User authenticatedUser =
        userRepository.findUserByEmail(
            SecurityContextHolder.getContext().getAuthentication().getName());
    if (authenticatedUser.getRole().getType().equals("MENTOR")
        && !moduleItem
            .getModule()
            .getCourse()
            .getMentor()
            .getEmail()
            .equals(authenticatedUser.getEmail())) {
      throw new IllegalArgumentException();
    }
    Set<ModuleItem> moduleItems = moduleItem.getModule().getModuleItems();
    if (moduleItems.size() == 1) {
      throw new UnsupportedOperationException(
          messageSource.getMessage("moduleItem.delete.not.allowed", null, Locale.ROOT));
    }
    moduleItems.remove(moduleItem);
    moduleItemRepository.delete(moduleItem);
  }
}
