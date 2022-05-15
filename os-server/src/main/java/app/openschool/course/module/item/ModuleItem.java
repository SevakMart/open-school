package app.openschool.course.module.item;

import app.openschool.course.module.Module;
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

  @Column(name = "module_item_type", nullable = false)
  private String moduleItemType;

  @Column(name = "estimated_time")
  private Long estimatedTime;

  @ManyToOne
  @JoinColumn(name = "module_id")
  private Module module;

  public ModuleItem() {}

  public ModuleItem(Long id, String moduleItemType, Long estimatedTime, Module module) {
    this.id = id;
    this.moduleItemType = moduleItemType;
    this.estimatedTime = estimatedTime;
    this.module = module;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getModuleItemType() {
    return moduleItemType;
  }

  public void setModuleItemType(String moduleItemType) {
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
}
