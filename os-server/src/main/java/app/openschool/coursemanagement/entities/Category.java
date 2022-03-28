package app.openschool.coursemanagement.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class Category {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Integer id;

  @Column(nullable = false)
  private String title;

  @Column(name = "parent_category_id")
  private Integer parentCategoryId;

  @Column(name = "logo_path")
  private String logoPath;

  @Column(name = "sub_category_count")
  private Integer subCategoryCount;

  @OneToMany(mappedBy = "category")
  private List<Course> courses;

  public Category() {}

  public Category(Integer id) {
    this.id = id;
  }

  public Category(String title, Integer parentCategoryId) {
    this.title = title;
    this.parentCategoryId = parentCategoryId;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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

  public void setParentCategoryId(Integer parentCategoryId) {
    this.parentCategoryId = parentCategoryId;
  }

  public Integer getParentCategoryId() {
    return parentCategoryId;
  }
}
