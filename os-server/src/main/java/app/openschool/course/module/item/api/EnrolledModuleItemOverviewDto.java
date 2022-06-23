package app.openschool.course.module.item.api;

public class EnrolledModuleItemOverviewDto {

  private final String type;

  private final String name;

  private final long estimatedTime;

  private final String link;

  private final String status;

  public EnrolledModuleItemOverviewDto(
      String type, String name, long estimatedTime, String link, String status) {
    this.type = type;
    this.name = name;
    this.estimatedTime = estimatedTime;
    this.link = link;
    this.status = status;
  }

  public String getType() {
    return type;
  }

  public String getName() {
    return name;
  }

  public long getEstimatedTime() {
    return estimatedTime;
  }

  public String getLink() {
    return link;
  }

  public String getStatus() {
    return status;
  }
}
