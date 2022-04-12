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
@Table(name = "module_status")
public class ModuleStatus {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(name = "status_type", nullable = false)
  private String type;

  @OneToMany(mappedBy = "moduleStatus")
  private Set<Module> modules;

  public ModuleStatus() {}

  public ModuleStatus(String type) {
    this.type = type;
  }

  public ModuleStatus(Long id, String type, Set<Module> modules) {
    this.id = id;
    this.type = type;
    this.modules = modules;
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

  public Set<Module> getModules() {
    return modules;
  }

  public void setModules(Set<Module> modules) {
    this.modules = modules;
  }
}
