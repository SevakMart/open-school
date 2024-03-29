package app.openschool.course.module.api.mapper;

import app.openschool.course.module.EnrolledModule;
import app.openschool.course.module.Module;
import app.openschool.course.module.api.dto.EnrolledModuleOverviewDto;
import app.openschool.course.module.item.ModuleItem;
import app.openschool.course.module.item.api.mapper.EnrolledModuleItemMapper;
import java.util.HashSet;
import java.util.Set;

public class EnrolledModuleMapper {

  private EnrolledModuleMapper(){}

  public static Set<EnrolledModuleOverviewDto> toEnrolledModuleOverviewDtoList(
      Set<EnrolledModule> enrolledModuleSet) {
    Set<EnrolledModuleOverviewDto> enrolledModuleOverviewDtoSet = new HashSet<>();
    enrolledModuleSet.forEach(
        enrolledModule ->
            enrolledModuleOverviewDtoSet.add(toEnrolledModuleOverviewDto(enrolledModule)));
    return enrolledModuleOverviewDtoSet;
  }

  public static EnrolledModuleOverviewDto toEnrolledModuleOverviewDto(
      EnrolledModule enrolledModule) {
    return new EnrolledModuleOverviewDto(
        enrolledModule.getModule().getTitle(),
        enrolledModule.getModuleStatus().getType(),
        getModuleEstimatedTime(enrolledModule.getModule()),
        EnrolledModuleItemMapper.toEnrolledModuleItemOverviewDtoList(
            enrolledModule.getEnrolledModuleItems()));
  }

  private static long getModuleEstimatedTime(Module module) {
    return module.getModuleItems().stream().mapToLong(ModuleItem::getEstimatedTime).sum();
  }
}
