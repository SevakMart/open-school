package app.openschool.usermanagement.repository;

import app.openschool.usermanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Query(value = "SELECT * FROM user u JOIN role r ON u.role_id = r.id JOIN company c "
            + "ON u.company_id = c.id WHERE r.role_type = 'mentor'", nativeQuery = true)
    List<UserEntity> findAllMentors();

}
