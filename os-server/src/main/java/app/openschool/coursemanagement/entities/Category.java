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
@Table (name = "category")
public class Category {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Column (nullable = false)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(name = "parent_category_id")
  private Integer parentCategoryId;

  @Column(name = "logo_path")
  private String logoPath;

  @Column(name = "sub_category_count")
  private Integer subCategoryCount;

  @OneToMany (mappedBy = "category")
  private List<Course> courses;

  public Category() {}

  public Long getId() {
    return id;
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

  public Integer getParentCategoryId() {
    return parentCategoryId;
  }

  public List<Course> getCourses() {
    return courses;
  }

  public void setSubCategoryCount(Integer subCategoryCount) {
    this.subCategoryCount = subCategoryCount;
  }

  public void setId(Long id) {
    this.id = id;
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
}
