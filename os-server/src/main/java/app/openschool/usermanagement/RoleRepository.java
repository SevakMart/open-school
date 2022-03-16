package app.openschool.usermanagement;

import app.openschool.usermanagement.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/** Useful Javadoc. */
public interface RoleRepository extends JpaRepository<Role, Integer> {

  Role findRoleEntityByType(String roleType);
}
