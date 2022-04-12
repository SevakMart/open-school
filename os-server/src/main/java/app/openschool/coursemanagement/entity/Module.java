package app.openschool.coursemanagement.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "module")
public class Module {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "learning_path_id")
  private Course course;

  @ManyToOne
  @JoinColumn(name = "module_status_id")
  private ModuleStatus moduleStatus;

  @OneToMany(mappedBy = "module")
  private Set<ModuleItem> moduleItems;

  public Module() {}

  public Module(Course course, ModuleStatus moduleStatus) {
    this.course = course;
    this.moduleStatus = moduleStatus;
  }

  public Module(Long id, Course course, Set<ModuleItem> moduleItems, ModuleStatus moduleStatus) {
    this.id = id;
    this.course = course;
    this.moduleItems = moduleItems;
    this.moduleStatus = moduleStatus;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

  public Set<ModuleItem> getModuleItems() {
    return moduleItems;
  }

  public void setModuleItems(Set<ModuleItem> moduleItems) {
    this.moduleItems = moduleItems;
  }

  public ModuleStatus getModuleStatus() {
    return moduleStatus;
  }

  public void setModuleStatus(ModuleStatus moduleStatus) {
    this.moduleStatus = moduleStatus;
  }
}
