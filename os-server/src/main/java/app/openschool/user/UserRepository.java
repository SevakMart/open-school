package app.openschool.user;

import app.openschool.user.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

/** Useful Javadoc. */
public interface UserRepository extends JpaRepository<User, Long> {}
