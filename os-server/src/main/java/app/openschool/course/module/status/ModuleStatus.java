package app.openschool.course.module.status;

import app.openschool.course.module.EnrolledModule;
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
  private Set<EnrolledModule> enrolledModules;

  public ModuleStatus() {}

  public ModuleStatus(Long id) {
    this.id = id;
  }

  public ModuleStatus(String type) {
    this.type = type;
  }

  public ModuleStatus(Long id, String type, Set<EnrolledModule> modules) {
    this.id = id;
    this.type = type;
    this.enrolledModules = modules;
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

  public Set<EnrolledModule> getEnrolledModules() {
    return enrolledModules;
  }

  public void setEnrolledModules(Set<EnrolledModule> enrolledModules) {
    this.enrolledModules = enrolledModules;
  }
}
