package app.openschool.coursemanagement.entities;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table (name = "learning_path")
public class Course {

  @Id
  @GeneratedValue (strategy = GenerationType.IDENTITY)
  @Column (nullable = false)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description")
  private String description;

  @ManyToOne
  @JoinColumn (name = "category_id")
  private Category category;

  public Course() {}

  public Course(String title, String description, Category category) {
    this.title = title;
    this.description = description;
    this.category = category;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Long getId() {
    return id;
  }

  public String getTitle() {
    return title;
  }

  public String getDescription() {
    return description;
  }

  public Category getCategory() {
    return category;
  }
}
