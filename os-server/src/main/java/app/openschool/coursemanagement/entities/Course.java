package app.openschool.coursemanagement.entities;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "learning_path")
public class Course {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "description")
  private String description;

  @Column(name = "rating")
  private Double rating;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "difficulty_id")
  private Difficulty difficulty;

  @ManyToOne
  @JoinColumn(name = "language_id")
  private Language language;

  @ManyToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "keyword_learning_path",
      joinColumns = {@JoinColumn(name = "learning_path_id")},
      inverseJoinColumns = {@JoinColumn(name = "keyword_id")})
  private Set<Keyword> keywords;

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

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  public Difficulty getDifficulty() {
    return difficulty;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  public Language getLanguage() {
    return language;
  }

  public void setLanguage(Language language) {
    this.language = language;
  }

  public Set<Keyword> getKeywords() {
    return keywords;
  }

  public void setKeywords(Set<Keyword> keywords) {
    this.keywords = keywords;
  }
}
