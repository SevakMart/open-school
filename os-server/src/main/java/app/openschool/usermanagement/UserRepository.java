package app.openschool.usermanagement;

import app.openschool.usermanagement.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  User findUserByEmail(String email);
}
