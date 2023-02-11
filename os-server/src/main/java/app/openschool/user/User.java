package app.openschool.user;

import app.openschool.auth.entity.ResetPasswordToken;
import app.openschool.category.Category;
import app.openschool.course.Course;
import app.openschool.course.EnrolledCourse;
import app.openschool.discussion.DiscussionAnswer;
import app.openschool.discussion.DiscussionQuestion;
import app.openschool.user.company.Company;
import app.openschool.user.role.Role;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "enabled", nullable = false)
  private Boolean enabled = false;

  @Column(name = "first_name", nullable = false)
  private String name;

  @Column(name = "last_name")
  private String surname;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "profession_name")
  private String professionName;

  @Column(name = "course_count")
  private Integer courseCount;

  @Column(name = "user_img_path")
  private String userImgPath;

  @Column(name = "email_path")
  private String emailPath;

  @Column(name = "linkedin_path")
  private String linkedinPath;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private Company company;

  @ManyToMany
  @JoinTable(
      name = "category_user",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "category_id")})
  private Set<Category> categories;

  @ManyToMany
  @JoinTable(
      name = "user_saved_learning_paths",
      joinColumns = {@JoinColumn(name = "user_id")},
      inverseJoinColumns = {@JoinColumn(name = "learning_path_id")})
  private Set<Course> savedCourses = new HashSet<>();

  @OneToMany(mappedBy = "mentor")
  private Set<Course> courses;

  @OneToMany(cascade = CascadeType.MERGE, mappedBy = "user")
  private Set<EnrolledCourse> enrolledCourses = new HashSet<>();

  @OneToOne(mappedBy = "user")
  private ResetPasswordToken resetPasswordToken;

  @ManyToMany
  @JoinTable(
      name = "user_has_mentor",
      joinColumns = @JoinColumn(name = "user_id"),
      inverseJoinColumns = @JoinColumn(name = "mentor_id"))
  private Set<User> mentors = new HashSet<>();

  @OneToMany(mappedBy = "user")
  private List<DiscussionQuestion> discussionQuestionAskPeers;

  @OneToMany(mappedBy = "user")
  private List<DiscussionAnswer> discussionAnswers;

  public User() {}

  public User(Long id) {
    this.id = id;
  }

  public User(
      String firstName, String email, String password, Set<Category> categories, Role role) {
    this.name = firstName;
    this.email = email;
    this.password = password;
    this.categories = categories;
    this.role = role;
  }

  public User(String firstName, String email, String password, Role role) {
    this.name = firstName;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public User(String name, String surname, String email, String password, Role role) {
    this.name = name;
    this.surname = surname;
    this.email = email;
    this.password = password;
    this.role = role;
  }

  public User(String email, String password) {
    this.email = email;
    this.password = password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    User user = (User) o;
    return name.equals(user.name)
        && email.equals(user.email)
        && role.getId().equals(user.role.getId());
  }

  public Set<Category> getCategories() {
    return categories;
  }

  public void setCategories(Set<Category> categories) {
    this.categories = categories;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public String getUserImgPath() {
    return userImgPath;
  }

  public void setUserImgPath(String userImgPath) {
    this.userImgPath = userImgPath;
  }

  public Company getCompany() {
    return company;
  }

  public void setCompany(Company company) {
    this.company = company;
  }

  public Integer getCourseCount() {
    return courseCount;
  }

  public void setCourseCount(Integer courseTotalCount) {
    this.courseCount = courseTotalCount;
  }

  public String getProfessionName() {
    return professionName;
  }

  public void setProfessionName(String profession) {
    this.professionName = profession;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getSurname() {
    return surname;
  }

  public void setSurname(String lastName) {
    this.surname = lastName;
  }

  public String getName() {
    return name;
  }

  public void setName(String firstName) {
    this.name = firstName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEmailPath() {
    return emailPath;
  }

  public void setEmailPath(String emailPath) {
    this.emailPath = emailPath;
  }

  public String getLinkedinPath() {
    return linkedinPath;
  }

  public void setLinkedinPath(String linkedinPath) {
    this.linkedinPath = linkedinPath;
  }

  public Set<Course> getCourses() {
    return courses;
  }

  public void setCourses(Set<Course> courses) {
    this.courses = courses;
  }

  public ResetPasswordToken getResetPasswordToken() {
    return resetPasswordToken;
  }

  public void setResetPasswordToken(ResetPasswordToken resetPasswordToken) {
    this.resetPasswordToken = resetPasswordToken;
  }

  public Boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(Boolean enabled) {
    this.enabled = enabled;
  }

  public Set<EnrolledCourse> getEnrolledCourses() {
    return enrolledCourses;
  }

  public void setEnrolledCourses(Set<EnrolledCourse> enrolledCourses) {
    this.enrolledCourses = enrolledCourses;
  }

  public Set<Course> getSavedCourses() {
    return savedCourses;
  }

  public void setSavedCourses(Set<Course> savedCourses) {
    this.savedCourses = savedCourses;
  }

  public Set<User> getMentors() {
    return mentors;
  }

  public void setMentors(Set<User> mentors) {
    this.mentors = mentors;
  }

  public List<DiscussionQuestion> getDiscussionQuestion() {
    return discussionQuestionAskPeers;
  }

  public void setDiscussionQuestion(List<DiscussionQuestion> discussionQuestionAskPeers) {
    this.discussionQuestionAskPeers = discussionQuestionAskPeers;
  }
}
