package app.openschool.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class UserRegistrationDto {

  private static final String PASSWORD_PATTERN =
      "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_{}]).{8,20})";

  private static final String EMAIL_PATTERN =
      "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?"
          + "(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

  @Schema(description = "User name", example = "John")
  @NotBlank(message = "{validation.firstName.error.message}")
  private String firstName;

  @Schema(description = "Provided email by user", example = "Test777@gmail.com")
  @NotBlank(message = "{validation.email.blank.message}")
  @Email(regexp = EMAIL_PATTERN, message = "{validation.email.pattern.message}")
  private String email;

  @Schema(description = "Provided password by user", example = "Test777#")
  @NotBlank(message = "{validation.password.blank.message}")
  @Pattern(regexp = PASSWORD_PATTERN, message = "{validation.password.error.message}")
  private String password;

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
}
