package app.openschool.course;

import app.openschool.course.module.EnrolledModule;
import app.openschool.course.status.CourseStatus;
import app.openschool.user.User;
import java.time.LocalDate;
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
@Table(name = "enrolled_learning_path")
public class EnrolledCourse {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(name = "due_date")
  private LocalDate dueDate;

  @ManyToOne
  @JoinColumn(name = "learning_path_id")
  private Course course;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne
  @JoinColumn(name = "learning_path_status_id")
  private CourseStatus courseStatus;

  @OneToMany(cascade = CascadeType.MERGE, mappedBy = "enrolledCourse")
  private Set<EnrolledModule> enrolledModules;

  public EnrolledCourse(LocalDate dueDate, Course course, User user, CourseStatus courseStatus) {
    this.dueDate = dueDate;
    this.course = course;
    this.user = user;
    this.courseStatus = courseStatus;
  }

  public EnrolledCourse() {}

  public EnrolledCourse(Long id) {
    this.id = id;
  }

  public EnrolledCourse(
      Long id,
      LocalDate dueDate,
      Course course,
      User user,
      CourseStatus courseStatus,
      Set<EnrolledModule> enrolledModules) {
    this.id = id;
    this.dueDate = dueDate;
    this.course = course;
    this.user = user;
    this.courseStatus = courseStatus;
    this.enrolledModules = enrolledModules;
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

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public CourseStatus getCourseStatus() {
    return courseStatus;
  }

  public void setCourseStatus(CourseStatus courseStatus) {
    this.courseStatus = courseStatus;
  }

  public Set<EnrolledModule> getEnrolledModules() {
    return enrolledModules;
  }

  public void setEnrolledModules(Set<EnrolledModule> enrolledModules) {
    this.enrolledModules = enrolledModules;
  }

  public LocalDate getDueDate() {
    return dueDate;
  }

  public void setDueDate(LocalDate dueDate) {
    this.dueDate = dueDate;
  }
}
