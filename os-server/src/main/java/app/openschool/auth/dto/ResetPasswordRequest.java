package app.openschool.auth.dto;

import javax.validation.constraints.Pattern;

public class ResetPasswordRequest {

  private static final String PASSWORD_PATTERN =
      "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[~!@#$%^&*()_{}]).{8,20})";

  private String token;

  @Pattern(regexp = PASSWORD_PATTERN, message = "{validation.password.error.message}")
  private String newPassword;

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
