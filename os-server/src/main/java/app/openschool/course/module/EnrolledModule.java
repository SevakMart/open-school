package app.openschool.course.module;

import app.openschool.course.EnrolledCourse;
import app.openschool.course.module.item.EnrolledModuleItem;
import app.openschool.course.module.quiz.EnrolledQuiz;
import app.openschool.course.module.status.ModuleStatus;
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
@Table(name = "enrolled_module")
public class EnrolledModule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "module_id")
  private Module module;

  @ManyToOne
  @JoinColumn(name = "enrolled_learning_path_id")
  private EnrolledCourse enrolledCourse;

  @ManyToOne
  @JoinColumn(name = "module_status_id")
  private ModuleStatus moduleStatus;

  @OneToMany(cascade = CascadeType.MERGE, mappedBy = "enrolledModule")
  private Set<EnrolledModuleItem> enrolledModuleItems;

  @OneToMany(cascade = CascadeType.MERGE, mappedBy = "enrolledModule")
  private Set<EnrolledQuiz> enrolledQuizzes;

  public EnrolledModule() {}

  public EnrolledModule(Module module, ModuleStatus moduleStatus, EnrolledCourse enrolledCourse) {
    this.module = module;
    this.moduleStatus = moduleStatus;
    this.enrolledCourse = enrolledCourse;
  }

  public EnrolledModule(
      Long id,
      Module module,
      EnrolledCourse enrolledCourse,
      ModuleStatus moduleStatus,
      Set<EnrolledModuleItem> enrolledModuleItems) {
    this.id = id;
    this.module = module;
    this.enrolledCourse = enrolledCourse;
    this.moduleStatus = moduleStatus;
    this.enrolledModuleItems = enrolledModuleItems;
  }

  public static EnrolledModule getInstance() {
    return new EnrolledModule();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Module getModule() {
    return module;
  }

  public void setModule(Module module) {
    this.module = module;
  }

  public EnrolledCourse getEnrolledCourse() {
    return enrolledCourse;
  }

  public void setEnrolledCourse(EnrolledCourse enrolledCourse) {
    this.enrolledCourse = enrolledCourse;
  }

  public ModuleStatus getModuleStatus() {
    return moduleStatus;
  }

  public void setModuleStatus(ModuleStatus moduleStatus) {
    this.moduleStatus = moduleStatus;
  }

  public Set<EnrolledModuleItem> getEnrolledModuleItems() {
    return enrolledModuleItems;
  }

  public void setEnrolledModuleItems(Set<EnrolledModuleItem> enrolledModuleItems) {
    this.enrolledModuleItems = enrolledModuleItems;
  }

  public Set<EnrolledQuiz> getEnrolledQuizzes() {
    return enrolledQuizzes;
  }

  public void setEnrolledQuizzes(Set<EnrolledQuiz> enrolledQuizzes) {
    this.enrolledQuizzes = enrolledQuizzes;
  }
}
