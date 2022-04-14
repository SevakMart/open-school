package app.openschool.course.module.item;

import app.openschool.course.module.Module;
import app.openschool.course.module.item.status.ModuleItemStatus;
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

  @Column(name = "grade")
  private Integer grade;

  @ManyToOne
  @JoinColumn(name = "module_id")
  private Module module;

  @ManyToOne
  @JoinColumn(name = "module_item_status_id")
  private ModuleItemStatus moduleItemStatus;

  public ModuleItem() {}

  public ModuleItem(
      Long id,
      String moduleItemType,
      Long estimatedTime,
      Integer grade,
      Module module,
      ModuleItemStatus moduleItemStatus) {
    this.id = id;
    this.moduleItemType = moduleItemType;
    this.estimatedTime = estimatedTime;
    this.grade = grade;
    this.module = module;
    this.moduleItemStatus = moduleItemStatus;
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

  public Integer getGrade() {
    return grade;
  }

  public void setGrade(Integer grade) {
    this.grade = grade;
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  public ModuleItemStatus getModuleItemStatus() {
    return moduleItemStatus;
  }

  public void setModuleItemStatus(ModuleItemStatus moduleItemStatus) {
    this.moduleItemStatus = moduleItemStatus;
  }
}
