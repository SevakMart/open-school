package app.openschool.usermanagement.repository;

import app.openschool.usermanagement.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Integer> {
}
