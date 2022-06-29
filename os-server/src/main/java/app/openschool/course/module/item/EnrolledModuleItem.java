package app.openschool.course.module.item;

import app.openschool.course.module.EnrolledModule;
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
@Table(name = "enrolled_module_item")
public class EnrolledModuleItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(name = "grade")
  private Integer grade;

  @ManyToOne
  @JoinColumn(name = "module_item_id")
  private ModuleItem moduleItem;

  @ManyToOne
  @JoinColumn(name = "enrolled_module_id")
  private EnrolledModule enrolledModule;

  @ManyToOne
  @JoinColumn(name = "module_item_status_id")
  private ModuleItemStatus moduleItemStatus;

  public EnrolledModuleItem() {}

  public EnrolledModuleItem(
      ModuleItem moduleItem, EnrolledModule enrolledModule, ModuleItemStatus moduleItemStatus) {
    this.moduleItem = moduleItem;
    this.enrolledModule = enrolledModule;
    this.moduleItemStatus = moduleItemStatus;
  }

  public EnrolledModuleItem(
      Long id,
      ModuleItem moduleItem,
      EnrolledModule enrolledModule,
      ModuleItemStatus moduleItemStatus) {
    this.id = id;
    this.moduleItem = moduleItem;
    this.enrolledModule = enrolledModule;
    this.moduleItemStatus = moduleItemStatus;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getGrade() {
    return grade;
  }

  public void setGrade(Integer grade) {
    this.grade = grade;
  }

  public ModuleItem getModuleItem() {
    return moduleItem;
  }

  public void setModuleItem(ModuleItem moduleItem) {
    this.moduleItem = moduleItem;
  }

  public EnrolledModule getEnrolledModule() {
    return enrolledModule;
  }

  public void setEnrolledModule(EnrolledModule enrolledModule) {
    this.enrolledModule = enrolledModule;
  }

  public ModuleItemStatus getModuleItemStatus() {
    return moduleItemStatus;
  }

  public void setModuleItemStatus(ModuleItemStatus moduleItemStatus) {
    this.moduleItemStatus = moduleItemStatus;
  }
}
