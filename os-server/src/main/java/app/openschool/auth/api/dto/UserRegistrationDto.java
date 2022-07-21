package app.openschool.auth.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UserRegistrationDto {

  /*
  Password should be at least 8 characters at most 45 characters and contain
  at least one uppercase, lowercase, number and special character.
  */
  private static final String PSD_PATTERN =
      "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_{}]).{8,20})";

  /*
   Email format allows at least one character before '@', '@' is required,
   at least 2 characters after '@', '.' is required, at least 2 characters after '.',
   digits not allowed after '.', email length must not increase 45 characters.
  */
  private static final String EMAIL_PATTERN =
      "^(?=.{1,45}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-]"
          + "[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

  @Schema(description = "User name", example = "John")
  @Length(max = 45, message = "{validation.firstName.length.message}")
  @NotBlank(message = "{validation.firstName.error.message}")
  private String firstName;

  @Schema(description = "User surname", example = "Smith")
  @Length(max = 45, message = "{validation.lastName.length.message}")
  @NotBlank(message = "{validation.lastName.error.message}")
  private String lastName;

  @Schema(description = "Provided email by user", example = "Test777@gmail.com")
  @NotBlank(message = "{validation.email.blank.message}")
  @Length(max = 45, message = "{validation.email.length.message}")
  @Email(regexp = EMAIL_PATTERN, message = "{validation.email.pattern.message}")
  private String email;

  @Schema(description = "Provided password by user", example = "Test777#")
  @NotBlank(message = "{validation.password.blank.message}")
  @Pattern(regexp = PSD_PATTERN, message = "{validation.password.error.message}")
  private String psd;

  public UserRegistrationDto() {}

  public UserRegistrationDto(String firstName, String lastName, String email, String psd) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.psd = psd;
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

  public String getPsd() {
    return psd;
  }

  public void setPsd(String psd) {
    this.psd = psd;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }
}
