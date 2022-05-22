package app.openschool.auth.verification;

import app.openschool.user.User;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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

  @Column(name = "created_date", nullable = false)
  private Instant createdAt;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  public VerificationToken() {}

  public VerificationToken(String token, Instant createdAt, User user) {
    this.token = token;
    this.createdAt = createdAt;
    this.user = user;
  }

  public static VerificationToken generateVerificationToken(User user) {
    String token = UUID.randomUUID().toString() + user.getId();
    Instant createdAt = Instant.now();
    return new VerificationToken(token, createdAt, user);
  }

  public boolean isTokenExpired(Instant createdAt, long expiresAt) {
    Duration difference = Duration.between(createdAt, Instant.now());
    return difference.toMinutes() > expiresAt;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Instant createdAt) {
    this.createdAt = createdAt;
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
}
