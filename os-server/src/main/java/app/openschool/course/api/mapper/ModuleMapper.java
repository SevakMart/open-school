package app.openschool.course.api.mapper;

import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.item.EnrolledModuleItem;
import app.openschool.course.module.item.status.ModuleItemStatus;
import app.openschool.course.module.status.ModuleStatus;
import java.util.Set;
import java.util.stream.Collectors;

public class ModuleMapper {

  public static Set<EnrolledModule> toEnrolledModules(
      Course course, EnrolledCourse enrolledCourse) {
    Set<EnrolledModule> enrolledModules =
        course.getModules().stream()
            .map(module -> new EnrolledModule(module, ModuleStatus.inProgress(), enrolledCourse))
            .collect(Collectors.toSet());

    return enrolledModules.stream()
        .map(
            enrolledModule -> {
              enrolledModule.setEnrolledModuleItems(getEnrolledModuleItems(enrolledModule));
              return enrolledModule;
            })
        .collect(Collectors.toSet());
  }

  private static Set<EnrolledModuleItem> getEnrolledModuleItems(EnrolledModule enrolledModule) {
    return enrolledModule.getModule().getModuleItems().stream()
        .map(
            moduleItem ->
                new EnrolledModuleItem(moduleItem, enrolledModule, ModuleItemStatus.inProgress()))
        .collect(Collectors.toSet());
  }
}
