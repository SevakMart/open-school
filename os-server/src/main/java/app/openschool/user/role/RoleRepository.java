package app.openschool.user.role;

import app.openschool.user.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;



public interface RoleRepository extends JpaRepository<Role, Integer> {
}
