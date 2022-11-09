package app.openschool.user;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  User findUserByEmail(String email);

  Optional<User> findByEmail(String email);

  @Query(
      value =
          "SELECT * FROM user u JOIN user_role r ON u.role_id = r.id JOIN company c "
              + "ON u.company_id = c.id WHERE r.role_type = 'MENTOR'",
      nativeQuery = true)
  Page<User> findAllMentors(Pageable page);

  @Query(
      value =
          "SELECT * FROM user WHERE user.role_id = 3 "
              + "AND (LOWER(CONCAT(first_name,' ',last_name))) "
              + "LIKE LOWER(CONCAT('%', ?1, '%'))",
      nativeQuery = true)
  Optional<Page<User>> findMentorsByName(String name, Pageable pageable);

  @Query(
      value =
          "SELECT * FROM user u WHERE (u.id IN "
              + "(SELECT mentor_id FROM user_has_mentor "
              + "WHERE user_id = ?1)) AND "
              + "(?2 IS NULL OR LOWER(CONCAT(first_name,' ',last_name)) "
              + "LIKE LOWER(CONCAT('%', ?2, '%')))",
      nativeQuery = true)
  Page<User> findSavedMentorsByName(Long userId, String name, Pageable pageable);
}
