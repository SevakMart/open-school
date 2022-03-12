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
  @Column(name = "user_id", nullable = false)
  private Long id;

  @Column(name = "first_name", nullable = false)
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "phone")
  private Integer phone;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  @Column(name = "profession")
  private String profession;

  @Column(name = "course_total_count")
  private Integer courseTotalCount;

  @Column(name = "user_photo")
  private byte[] userPhoto;

  @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
  private Set<UserRole> userRoles = new HashSet<>();

  public User() {}

  /** Useful Javadoc. */
  public User(String firstName, String email, String password) {
    this.firstName = firstName;
    this.email = email;
    this.password = password;
  }

  public Set<UserRole> getUserRoles() {
    return userRoles;
  }

  public void setUserRoles(Set<UserRole> userRoles) {
    this.userRoles = userRoles;
  }

  public byte[] getUserPhoto() {
    return userPhoto;
  }

  public void setUserPhoto(byte[] userPhoto) {
    this.userPhoto = userPhoto;
  }

  public Integer getCourseTotalCount() {
    return courseTotalCount;
  }

  public void setCourseTotalCount(Integer courseTotalCount) {
    this.courseTotalCount = courseTotalCount;
  }

  public String getProfession() {
    return profession;
  }

  public void setProfession(String profession) {
    this.profession = profession;
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

  public Integer getPhone() {
    return phone;
  }

  public void setPhone(Integer phone) {
    this.phone = phone;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }
}
