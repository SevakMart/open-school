package app.openschool.auth.entity;

import app.openschool.user.User;
import java.time.Duration;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.apache.commons.lang3.RandomStringUtils;

@Entity
@Table(name = "reset_password_token")
public class ResetPasswordToken {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false)
  private Long id;

  @Column(nullable = false)
  private String token;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @OneToOne
  @JoinColumn(name = "user_id")
  private User user;

  private ResetPasswordToken() {}

  private ResetPasswordToken(String token, LocalDateTime createdAt, User user) {
    this.token = token;
    this.createdAt = createdAt;
    this.user = user;
  }

  public static ResetPasswordToken generate(User user) {
    return new ResetPasswordToken(
        RandomStringUtils.random(4, false, true), LocalDateTime.now(), user);
  }

  public boolean isExpired(int tokenExpirationDurationAfterMinutes) {
    LocalDateTime now = LocalDateTime.now();
    Duration diff = Duration.between(this.createdAt, now);
    return diff.toMinutes() > tokenExpirationDurationAfterMinutes;
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

  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(LocalDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }
}
