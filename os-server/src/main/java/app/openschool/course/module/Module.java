package app.openschool.course.module;

import app.openschool.course.Course;
import app.openschool.course.module.item.ModuleItem;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
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

  @Column(name = "title")
  private String title;

  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn(name = "learning_path_id")
  private Course course;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "module")
  private Set<ModuleItem> moduleItems = new HashSet<>();

  public Module() {}

  public Module(Course course) {
    this.course = course;
  }

  public Module(Long id, Course course, Set<ModuleItem> moduleItems) {
    this.id = id;
    this.course = course;
    this.moduleItems = moduleItems;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
