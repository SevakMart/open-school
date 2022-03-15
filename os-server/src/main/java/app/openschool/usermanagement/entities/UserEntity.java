package app.openschool.usermanagement.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** Useful Javadoc. */
@Entity
@Table(name = "user")
public class UserEntity {
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

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private RoleEntity role;

  @ManyToOne
  @JoinColumn(name = "company_id")
  private CompanyEntity company;

  public UserEntity() {}

  /** Useful Javadoc. */
  public UserEntity(String firstName, String email, String password) {
    this.name = firstName;
    this.email = email;
    this.password = password;
  }

  public RoleEntity getRole() {
    return role;
  }

  public void setRole(RoleEntity role) {
    this.role = role;
  }

  public String getUserImgPath() {
    return userImgPath;
  }

  public void setUserImgPath(String userImgPath) {
    this.userImgPath = userImgPath;
  }

  public CompanyEntity getCompany() {
    return company;
  }

  public void setCompany(CompanyEntity company) {
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
}
