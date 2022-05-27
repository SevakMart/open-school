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

  public static Set<EnrolledModule> toEnrolledModuleSet(
      Course course, EnrolledCourse enrolledCourse) {
    Set<EnrolledModule> enrolledModules =
        course.getModules().stream()
            .map(module -> new EnrolledModule(module, new ModuleStatus(1L), enrolledCourse))
            .collect(Collectors.toSet());

    return enrolledModules.stream()
        .peek(
            enrolledModule -> enrolledModule.setEnrolledModuleItems(
                enrolledModule.getModule().getModuleItems().stream()
                    .map(
                        moduleItem ->
                            new EnrolledModuleItem(
                                moduleItem, enrolledModule, new ModuleItemStatus(1L)))
                    .collect(Collectors.toSet())))
        .collect(Collectors.toSet());
  }
}
