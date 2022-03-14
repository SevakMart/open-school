package app.openschool.usermanagement;

import app.openschool.usermanagement.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/** Useful Javadoc. */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

  UserEntity findUserByEmail(String email);
}
