package app.openschool.usermanagement;

import app.openschool.usermanagement.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/** Useful Javadoc. */
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {

  RoleEntity findRoleEntityByType(String roleType);
}
