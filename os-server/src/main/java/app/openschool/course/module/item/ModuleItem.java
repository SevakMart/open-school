package app.openschool.course.module.item;

import app.openschool.course.module.Module;
import app.openschool.course.module.item.type.ModuleItemType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "module_item")
public class ModuleItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @ManyToOne
  @JoinColumn(name = "module_item_type_id", nullable = false)
  private ModuleItemType moduleItemType;

  @Column(name = "link")
  private String link;

  @Column(name = "estimated_time")
  private Long estimatedTime;

  @ManyToOne
  @JoinColumn(name = "module_id")
  private Module module;

  public ModuleItem() {}

  public ModuleItem(
      Long id,
      String title,
      ModuleItemType moduleItemType,
      String link,
      Long estimatedTime,
      Module module) {
    this.id = id;
    this.title = title;
    this.moduleItemType = moduleItemType;
    this.link = link;
    this.estimatedTime = estimatedTime;
    this.module = module;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public ModuleItemType getModuleItemType() {
    return moduleItemType;
  }

  public void setModuleItemType(ModuleItemType moduleItemType) {
    this.moduleItemType = moduleItemType;
  }

  public Long getEstimatedTime() {
    return estimatedTime;
  }

  public void setEstimatedTime(Long estimatedTime) {
    this.estimatedTime = estimatedTime;
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
