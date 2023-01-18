package app.openschool.course.module.item.api.mapper;

import app.openschool.course.module.item.EnrolledModuleItem;
import app.openschool.course.module.item.api.dto.EnrolledModuleItemOverviewDto;
import java.util.HashSet;
import java.util.Set;

public class EnrolledModuleItemMapper {

  public static Set<EnrolledModuleItemOverviewDto> toEnrolledModuleItemOverviewDtoList(
      Set<EnrolledModuleItem> enrolledModuleItemSet) {
    Set<EnrolledModuleItemOverviewDto> enrolledModuleItemOverviewDtoSet = new HashSet<>();
    enrolledModuleItemSet.forEach(
        enrolledModuleItem ->
            enrolledModuleItemOverviewDtoSet.add(
                toEnrolledModuleItemOverviewDto(enrolledModuleItem)));
    return enrolledModuleItemOverviewDtoSet;
  }

  public static EnrolledModuleItemOverviewDto toEnrolledModuleItemOverviewDto(
      EnrolledModuleItem enrolledModuleItem) {
    return new EnrolledModuleItemOverviewDto(
        enrolledModuleItem.getId(),
        enrolledModuleItem.getModuleItem().getModuleItemType().getType(),
        enrolledModuleItem.getModuleItem().getTitle(),
        enrolledModuleItem.getModuleItem().getEstimatedTime(),
        enrolledModuleItem.getModuleItem().getLink(),
        enrolledModuleItem.getModuleItemStatus().getType());
  }
}
