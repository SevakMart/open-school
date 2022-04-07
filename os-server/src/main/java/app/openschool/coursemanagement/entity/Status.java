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
@Table(name = "status")
public class Status {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(name = "status_type", nullable = false)
  private String type;

  @OneToMany(mappedBy = "status")
  private Set<ModuleItem> moduleItems;

  @OneToMany(mappedBy = "status")
  private Set<Module> modules;

  @OneToMany(mappedBy = "status")
  private Set<Course> courses;

  public Status() {}

  public Status(
      Long id, String type, Set<ModuleItem> moduleItems, Set<Module> modules, Set<Course> courses) {
    this.id = id;
    this.type = type;
    this.moduleItems = moduleItems;
    this.modules = modules;
    this.courses = courses;
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

  public Set<Module> getModules() {
    return modules;
  }

  public void setModules(Set<Module> modules) {
    this.modules = modules;
  }

  public Set<Course> getCourses() {
    return courses;
  }

  public void setCourses(Set<Course> courses) {
    this.courses = courses;
  }
}
