package app.openschool.course.status;

import app.openschool.course.EnrolledCourse;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "learning_path_status")
public class CourseStatus {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(name = "status_type", nullable = false)
  private String type;

  @OneToMany(mappedBy = "courseStatus")
  private Set<EnrolledCourse> enrolledCourses;

  public CourseStatus() {}

  public CourseStatus(Long id) {
    this.id = id;
  }

  public CourseStatus(String type) {
    this.type = type;
  }

  public CourseStatus(Long id, String type, Set<EnrolledCourse> enrolledCourses) {
    this.id = id;
    this.type = type;
    this.enrolledCourses = enrolledCourses;
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

  public Set<EnrolledCourse> getEnrolledCourses() {
    return enrolledCourses;
  }

  public void setEnrolledCourses(Set<EnrolledCourse> enrolledCourses) {
    this.enrolledCourses = enrolledCourses;
  }
}
