package app.openschool.course.module.api;

import app.openschool.course.module.item.api.EnrolledModuleItemOverviewDto;
import java.util.Set;

public class EnrolledModuleOverviewDto {

  private final String title;

  private final String status;

  private final long estimatedTime;

  private final Set<EnrolledModuleItemOverviewDto> enrolledModulesItems;

  public EnrolledModuleOverviewDto(
      String title,
      String status,
      long estimatedTime,
      Set<EnrolledModuleItemOverviewDto> enrolledModulesItems) {
    this.title = title;
    this.status = status;
    this.estimatedTime = estimatedTime;
    this.enrolledModulesItems = enrolledModulesItems;
  }

  public String getTitle() {
    return title;
  }

  public String getStatus() {
    return status;
  }

  public long getEstimatedTime() {
    return estimatedTime;
  }

  public Set<EnrolledModuleItemOverviewDto> getEnrolledModulesItems() {
    return enrolledModulesItems;
  }
}
