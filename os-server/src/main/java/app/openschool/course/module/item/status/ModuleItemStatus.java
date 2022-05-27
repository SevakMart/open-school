package app.openschool.course.module.item.status;

import app.openschool.course.module.item.EnrolledModuleItem;
import app.openschool.course.module.item.ModuleItem;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "module_item_status")
public class ModuleItemStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(name = "status_type", nullable = false)
  private String type;

  @OneToMany(mappedBy = "moduleItemStatus")
  private Set<EnrolledModuleItem> enrolledModuleItems;

  public ModuleItemStatus() {}

  public ModuleItemStatus(String type) {
    this.type = type;
  }

  public ModuleItemStatus(Long id) {
    this.id = id;
  }

  public ModuleItemStatus(Long id, String type, Set<EnrolledModuleItem> enrolledModuleItems) {
    this.id = id;
    this.type = type;
    this.enrolledModuleItems = enrolledModuleItems;
  }

  public boolean isInProgress() {
    return this.type.equals("IN_PROGRESS");
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getType() {
    return type;
  }

  public Set<EnrolledModuleItem> getEnrolledModuleItems() {
    return enrolledModuleItems;
  }

  public void setEnrolledModuleItems(Set<EnrolledModuleItem> enrolledModuleItems) {
    this.enrolledModuleItems = enrolledModuleItems;
  }

  public void setType(String type) {
    this.type = type;
  }
}
