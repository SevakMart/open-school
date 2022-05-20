package app.openschool.auth.verification;

import app.openschool.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

  Optional<VerificationToken> findVerificationTokenByToken(String token);
  
  Optional<VerificationToken> findVerificationTokenByUser(User user);
}
