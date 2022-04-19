package app.openschool.auth.dto;

public class UserLoginRequest {
  private String password;
  private String email;

  public UserLoginRequest(String password, String email) {
    this.password = password;
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
