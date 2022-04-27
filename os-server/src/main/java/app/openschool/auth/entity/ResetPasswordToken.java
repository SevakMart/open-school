package app.openschool.auth.entity;

import app.openschool.user.User;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "reset_password_token")
public class ResetPasswordToken {

  private static final long EXPIRATION_DURATION_AFTER_MINUTES = 30;

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

  public ResetPasswordToken() {}

  public ResetPasswordToken(String token, LocalDateTime createdAt, User user) {
    this.token = token;
    this.createdAt = createdAt;
    this.user = user;
  }

  public ResetPasswordToken(Long id, String token, LocalDateTime createdAt, User user) {
    this.id = id;
    this.token = token;
    this.createdAt = createdAt;
    this.user = user;
  }

  public static ResetPasswordToken generateToken(User user) {
    return new ResetPasswordToken(
        String.valueOf(new Random().nextInt(9999)), LocalDateTime.now(), user);
  }

  public boolean isTokenExpired(final LocalDateTime createdAt) {
    LocalDateTime now = LocalDateTime.now();
    Duration diff = Duration.between(createdAt, now);
    return diff.toMinutes() > EXPIRATION_DURATION_AFTER_MINUTES;
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
