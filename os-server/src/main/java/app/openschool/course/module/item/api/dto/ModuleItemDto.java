package app.openschool.course.module.item.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

public class ModuleItemDto {

  @Schema(description = "ModuleItem Id", example = "1")
  private final Long id;

  @Schema(description = "ModuleItem title", example = "Synchronized blocks")
  private final String title;

  @Schema(description = "Id of the module item type", example = "3")
  private final Long ModuleItemTypeId;

  @Schema(description = "Link of the moduleItem", example = "https//1654hjjhj")
  private final String link;

  @Schema(description = "Estimated time of the moduleItem", example = "250")
  private final Long estimatedTime;

  public ModuleItemDto(
      Long id, String title, Long moduleItemTypeId, String link, Long estimatedTime) {
    this.id = id;
    this.title = title;
    ModuleItemTypeId = moduleItemTypeId;
    this.link = link;
    this.estimatedTime = estimatedTime;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public Long getModuleItemTypeId() {
    return ModuleItemTypeId;
  }

  public String getLink() {
    return link;
  }

  public Long getEstimatedTime() {
    return estimatedTime;
  }
}
