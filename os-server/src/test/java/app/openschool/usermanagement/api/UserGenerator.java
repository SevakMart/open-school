package app.openschool.usermanagement.api;

import app.openschool.usermanagement.entities.Company;
import app.openschool.usermanagement.entities.Role;
import app.openschool.usermanagement.entities.User;

public class UserGenerator {

  public static User generateUser() {
    User user = new User();
    user.setName("John");
    user.setSurname("Smith");
    user.setProfessionName("developer");
    user.setCourseCount(3);
    user.setUserImgPath("aaa");
    user.setLinkedinPath("kkk");
    user.setEmailPath("lll");
    user.setEmail("aaa@gmail.com");
    user.setPassword("pass");
    user.setId(1L);
    user.setRole(new Role("MENTOR"));
    Company company = new Company();
    company.setCompanyName("AAA");
    user.setCompany(company);
    return user;
  }
}
