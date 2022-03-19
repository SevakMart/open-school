package app.openschool.usermanagement.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

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

  public User() {}

  public User(String firstName, String email, String password, Role role) {
    this.name = firstName;
    this.email = email;
    this.password = password;
    this.role = role;
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
}
