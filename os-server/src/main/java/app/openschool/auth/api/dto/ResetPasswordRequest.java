package app.openschool.auth.api.dto;

import app.openschool.auth.api.annotation.SameValues;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@SameValues(sameFields = {"newPassword", "confirmedPassword"})
public class ResetPasswordRequest {

  private static final String PASSWORD_PATTERN =
      "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_{}]).{8,20})";

  @NotBlank(message = "{token.blank}")
  private String token;

  @NotBlank(message = "{password.blank}")
  @Pattern(regexp = PASSWORD_PATTERN, message = "{validation.password.error.message}")
  private String newPassword;

  @NotBlank(message = "{confirm.password.blank}")
  private String confirmedPassword;

  public ResetPasswordRequest() {}

  public ResetPasswordRequest(String token, String newPassword, String confirmedPassword) {
    this.token = token;
    this.newPassword = newPassword;
    this.confirmedPassword = confirmedPassword;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getConfirmedPassword() {
    return confirmedPassword;
  }

  public void setConfirmedPassword(String confirmedPassword) {
    this.confirmedPassword = confirmedPassword;
  }
}
