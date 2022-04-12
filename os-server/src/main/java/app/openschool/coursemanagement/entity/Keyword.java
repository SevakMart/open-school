package app.openschool.coursemanagement.entity;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "keyword")
public class Keyword {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  private String title;

  @ManyToMany
  @JoinTable(
      name = "keyword_learning_path",
      joinColumns = {@JoinColumn(name = "keyword_id")},
      inverseJoinColumns = {@JoinColumn(name = "learning_path_id")})
  private Set<Course> courses;

  public Keyword() {}

  public Keyword(String title) {
    this.title = title;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Set<Course> getCourses() {
    return courses;
  }

  public void setCourses(Set<Course> courses) {
    this.courses = courses;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
