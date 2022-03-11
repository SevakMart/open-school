package app.openschool.user.entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/** Useful Javadoc. */
@Entity
@Table(name = "user")
public class User {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long id;

  private String firstName;
  private String lastName;
  private Integer phone;
  private String email;
  private String password;
  private String profession;
  private Integer courseTotalCount;
  private Byte[] userPhoto;

  @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
  private Set<Role> userRoles = new HashSet<>();

  @OneToMany(mappedBy = "user",cascade = CascadeType.ALL)
  private Set<Company> companies = new HashSet<>();

  public User() {}

  /** Useful Javadoc. */
  public User(String firstName, String email, String password) {
    this.firstName = firstName;
    this.email = email;
    this.password = password;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public Integer getPhone() {
    return phone;
  }

  public void setPhone(Integer phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
  }

  public Integer getCourseTotalCount() {
    return courseTotalCount;
  }

  public void setCourseTotalCount(Integer courseTotal) {
    this.courseTotalCount = courseTotal;
  }

  public Byte[] getUserPhoto() {
    return userPhoto;
  }

  public void setUserPhoto(Byte[] userPhoto) {
    this.userPhoto = userPhoto;
  }

  public Set<Role> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(Set<Role> userRoles) {
    this.userRoles = userRoles;
  }

  public Set<Company> getCompanies() {
    return companies;
  }

  public void setCompanies(Set<Company> companies) {
    this.companies = companies;
  }
}
