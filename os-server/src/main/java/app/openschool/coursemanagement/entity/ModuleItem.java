package app.openschool.coursemanagement.entity;

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

  @Column(name = "duration")
  private Long estimatedTimeImMinutes;

  @Column(name = "grade")
  private Integer grade;

  @ManyToOne
  @JoinColumn(name = "module_id")
  private Module module;

  @ManyToOne
  @JoinColumn(name = "module_item_status_id")
  private Status status;

  public ModuleItem() {}

  public ModuleItem(
      Long id,
      String moduleItemType,
      Long estimatedTimeImMinutes,
      Integer grade,
      Module module,
      Status status) {
    this.id = id;
    this.moduleItemType = moduleItemType;
    this.estimatedTimeImMinutes = estimatedTimeImMinutes;
    this.grade = grade;
    this.module = module;
    this.status = status;
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

  public Long getEstimatedTimeImMinutes() {
    return estimatedTimeImMinutes;
  }

  public void setEstimatedTimeImMinutes(Long estimatedTimeImMinutes) {
    this.estimatedTimeImMinutes = estimatedTimeImMinutes;
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

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }
}
