package app.openschool.usermanagement.entities;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "role_type", nullable = false)
  private String type;

  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
  private Set<User> users;

  public Role() {}

  public Role(Integer id, String type) {
    this.id = id;
    this.type = type;
  }

  public Role(String type) {
    this.type = type;
  }

  public Set<User> getUsers() {
    return users;
  }

  public void setUsers(Set<User> users) {
    this.users = users;
  }

  public String getType() {
    return type;
  }

  public void setType(String roleType) {
    this.type = roleType;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
}
