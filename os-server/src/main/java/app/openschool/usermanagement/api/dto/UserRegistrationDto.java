package app.openschool.usermanagement.api.dto;

import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserRegistrationDto {

  private static final String PASSWORD_PATTERN =
      "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_{}]).{8,20})";

  @NotBlank(message = "{firstName.error.massage}")
  private String firstName;

  @NotBlank(message = "{email.error.massage}")
  private String email;

  @Pattern(regexp = PASSWORD_PATTERN, message = "{password.error.massage}")
  private String password;

  private Set<Long> categoryIdSet;

  public UserRegistrationDto() {}

  public UserRegistrationDto(String firstName, String email, String password) {
    this.firstName = firstName;
    this.email = email;
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Long> getCategoryIdSet() {
    return categoryIdSet;
  }

  public void setCategoryIdSet(Set<Long> categoryIdSet) {
    this.categoryIdSet = categoryIdSet;
  }
}
