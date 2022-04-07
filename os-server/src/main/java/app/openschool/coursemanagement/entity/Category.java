package app.openschool.coursemanagement.entity;

import app.openschool.usermanagement.entity.User;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(name = "parent_category_id")
  private Long parentCategoryId;

  @Column(name = "logo_path")
  private String logoPath;

  @Column(name = "sub_category_count")
  private Integer subCategoryCount;

  @OneToMany(mappedBy = "category")
  private List<Course> courses;

  @ManyToMany
  @JoinTable(
      name = "category_user",
      joinColumns = {@JoinColumn(name = "category_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id")})
  private Set<User> users;

  public Category() {}

  public Category(Long id) {
    this.id = id;
  }

  public Category(String title, Long parentCategoryId) {
    this.title = title;
    this.parentCategoryId = parentCategoryId;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public Integer getSubCategoryCount() {
    return subCategoryCount;
  }

  public String getLogoPath() {
    return logoPath;
  }

  public List<Course> getCourses() {
    return courses;
  }

  public void setSubCategoryCount(Integer subCategoryCount) {
    this.subCategoryCount = subCategoryCount;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setLogoPath(String logoPath) {
    this.logoPath = logoPath;
  }

  public void setParentCategoryId(Long parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
  }

  public Long getParentCategoryId() {
    return parentCategoryId;
  }

  public void setCourses(List<Course> courses) {
    this.courses = courses;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }
}
