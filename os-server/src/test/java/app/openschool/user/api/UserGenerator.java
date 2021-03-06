package app.openschool.user.api;

import app.openschool.user.User;
import app.openschool.user.company.Company;
import app.openschool.user.role.Role;

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

  public static User generateUserWithSavedMentors() {
    User user = new User();
    user.setName("John");
    user.setEmail("aaa@gmail.com");
    user.setPassword("pass");
    user.setRole(new Role("STUDENT"));
    user.getMentors().add(generateUser());
    return user;
  }
}
