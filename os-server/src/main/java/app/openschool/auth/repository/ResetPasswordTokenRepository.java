package app.openschool.auth.repository;

import app.openschool.auth.entity.ResetPasswordToken;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {

  Optional<ResetPasswordToken> findByToken(String token);

  @Query(value = "SELECT * FROM reset_password_token WHERE user_id = :userId", nativeQuery = true)
  Optional<ResetPasswordToken> findByUser(@Param(value = "userId") Long userId);
}
