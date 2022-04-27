package app.openschool.auth.verification;

import app.openschool.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

  VerificationToken findVerificationTokenByToken(String token);
  
  VerificationToken findVerificationTokenByUser(User user);
}
