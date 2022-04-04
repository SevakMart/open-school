package app.openschool.usermanagement.repository;

import app.openschool.usermanagement.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  User findUserByEmail(String email);

  @Query(
      value =
          "SELECT * FROM user u JOIN user_role r ON u.role_id = r.id JOIN company c "
              + "ON u.company_id = c.id WHERE r.role_type = 'MENTOR'",
      nativeQuery = true)
  Page<User> findAllMentors(Pageable page);
}
