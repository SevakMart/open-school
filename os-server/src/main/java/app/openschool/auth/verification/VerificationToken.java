package app.openschool.auth.verification;

import app.openschool.user.User;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "verification_token")
public class VerificationToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Long id;

  @Column(name = "token", nullable = false)
  private String token;

  @Column(name = "expires_data", nullable = false)
  private Long expiresAt;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public VerificationToken() {}

  public VerificationToken(User user, String token, Long expiresAt) {
    this.token = token;
    this.expiresAt = expiresAt;
    this.user = user;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Long getExpiresAt() {
    return expiresAt;
  }

  public void setExpiresAt(Long expiresAt) {
    this.expiresAt = expiresAt;
  }
}
