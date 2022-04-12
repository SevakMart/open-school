package app.openschool.coursemanagement.entity;

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
  private Set<ModuleItem> moduleItems;

  public ModuleItemStatus() {}

  public ModuleItemStatus(String type) {
    this.type = type;
  }

  public ModuleItemStatus(Long id, String type, Set<ModuleItem> moduleItems) {
    this.id = id;
    this.type = type;
    this.moduleItems = moduleItems;
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

  public void setType(String type) {
    this.type = type;
  }

  public Set<ModuleItem> getModuleItems() {
    return moduleItems;
  }

  public void setModuleItems(Set<ModuleItem> moduleItems) {
    this.moduleItems = moduleItems;
  }
}
