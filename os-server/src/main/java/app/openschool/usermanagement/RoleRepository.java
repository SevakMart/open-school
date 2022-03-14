package app.openschool.usermanagement;

import app.openschool.usermanagement.entities.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<RoleEntity,Integer> {
  
   RoleEntity findRoleEntityByRoleType(String roleType);
}
