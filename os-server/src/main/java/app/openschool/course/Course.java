package app.openschool.course;

import app.openschool.category.Category;
import app.openschool.course.difficulty.Difficulty;
import app.openschool.course.discussion.peers.question.PeersQuestion;
import app.openschool.course.keyword.Keyword;
import app.openschool.course.language.Language;
import app.openschool.course.module.Module;
import app.openschool.user.User;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
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

  @Column(name = "goal")
  private String goal;

  @Column(name = "rating")
  private Double rating = 0.0;

  @ManyToOne
  @JoinColumn(name = "category_id")
  private Category category;

  @ManyToOne
  @JoinColumn(name = "difficulty_id")
  private Difficulty difficulty;

  @ManyToOne
  @JoinColumn(name = "language_id")
  private Language language;

  @ManyToOne
  @JoinColumn(name = "mentor_id")
  private User mentor;

  @ManyToMany
  @JoinTable(
      name = "keyword_learning_path",
      joinColumns = {@JoinColumn(name = "learning_path_id")},
      inverseJoinColumns = {@JoinColumn(name = "keyword_id")})
  private Set<Keyword> keywords = new HashSet<>();

  @ManyToMany
  @JoinTable(
      name = "user_saved_learning_paths",
      joinColumns = {@JoinColumn(name = "learning_path_id")},
      inverseJoinColumns = {@JoinColumn(name = "user_id")})
  private Set<User> users = new HashSet<>();

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "course")
  private Set<Module> modules = new HashSet<>();

  @OneToMany(mappedBy = "course")
  private Set<EnrolledCourse> enrolledCourses = new HashSet<>();

  @OneToMany(mappedBy = "course")
  private List<PeersQuestion> peersQuestionAskPeers;

  public Course() {}

  public Course(Long id, String title) {
    this.id = id;
    this.title = title;
  }

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

  public void setId(Long id) {
    this.id = id;
  }

  public Set<Module> getModules() {
    return modules;
  }

  public void setModules(Set<Module> modules) {
    this.modules = modules;
  }

  public Set<EnrolledCourse> getEnrolledCourses() {
    return enrolledCourses;
  }

  public void setEnrolledCourses(Set<EnrolledCourse> enrolledCourses) {
    this.enrolledCourses = enrolledCourses;
  }

  public User getMentor() {
    return mentor;
  }

  public void setMentor(User mentor) {
    this.mentor = mentor;
  }

  public String getGoal() {
    return goal;
  }

  public void setGoal(String goal) {
    this.goal = goal;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }
}
