package app.openschool.usermanagement.api.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/** Useful Javadoc. */
public class UserRegistrationDto {

  private static final String PASSWORD_PATTERN =
      "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_{}]).{8,20})";
  private static final String PASSWORD_ERROR_MASSAGE =
      "Password should have 8-20 characters,"
          + "including upper and lower case letters,"
          + "numbers and signs(~!@#$%^&*()_{})";
  private static final String FIRST_NAME_ERROR_MASSAGE = "First name is mandatory";
  public static final String EMAIL_ERROR_MASSAGE = "Email is mandatory";

  @NotBlank(message = FIRST_NAME_ERROR_MASSAGE)
  private String firstName;

  @NotBlank(message = EMAIL_ERROR_MASSAGE)
  private String email;

  @Pattern(regexp = PASSWORD_PATTERN, message = PASSWORD_ERROR_MASSAGE)
  private String password;

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
}
