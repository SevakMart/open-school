package app.openschool.course.module.api;

import app.openschool.course.module.item.api.EnrolledModuleItemOverviewDto;
import java.util.List;

public class EnrolledModuleOverviewDto {

  private final String moduleTitle;

  private final String moduleStatus;

  private final long moduleEstimatedTime;

  private final List<EnrolledModuleItemOverviewDto> enrolledModules;

  public EnrolledModuleOverviewDto(
      String moduleTitle,
      String moduleStatus,
      long moduleEstimatedTime,
      List<EnrolledModuleItemOverviewDto> enrolledModules) {
    this.moduleTitle = moduleTitle;
    this.moduleStatus = moduleStatus;
    this.moduleEstimatedTime = moduleEstimatedTime;
    this.enrolledModules = enrolledModules;
  }

  public String getModuleTitle() {
    return moduleTitle;
  }

  public String getModuleStatus() {
    return moduleStatus;
  }

  public long getModuleEstimatedTime() {
    return moduleEstimatedTime;
  }

  public List<EnrolledModuleItemOverviewDto> getEnrolledModules() {
    return enrolledModules;
  }
}
