package app.openschool.auth;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

import app.openschool.auth.entity.ResetPasswordToken;
import app.openschool.auth.repository.ResetPasswordTokenRepository;
import app.openschool.user.User;
import app.openschool.user.UserRepository;
import app.openschool.user.company.Company;
import app.openschool.user.company.CompanyRepository;
import app.openschool.user.role.RoleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

@DataJpaTest
@DirtiesContext(classMode = AFTER_EACH_TEST_METHOD)
public class ResetPasswordTokenRepositoryTest {

  @Autowired ResetPasswordTokenRepository resetPasswordTokenRepository;
  @Autowired UserRepository userRepository;
  @Autowired CompanyRepository companyRepository;
  @Autowired RoleRepository roleRepository;

  @Test
  void findByUser() {
    User user = new User();
    user.setName("John");
    user.setEmail("aaa@gmail.com");
    user.setPassword("pass");
    user.setRole(roleRepository.getById(3));
    Company company = new Company();
    company.setCompanyName("AAA");
    companyRepository.save(company);
    user.setCompany(companyRepository.getById(1));
    userRepository.save(user);
    ResetPasswordToken resetPasswordToken = ResetPasswordToken.generate(user);
    resetPasswordTokenRepository.save(resetPasswordToken);
    userRepository.save(user);
    ResetPasswordToken fetchedResetPasswordToken =
        resetPasswordTokenRepository.findByUser(user.getId()).get();
    assertThat(resetPasswordToken.getToken()).isEqualTo(fetchedResetPasswordToken.getToken());
  }
}
