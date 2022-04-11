package app.openschool.usermanagement.api.dto;

public class UserLoginResponse {
  private String token;
  private UserLoginDto userLoginDto;

  public UserLoginResponse() {}

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public UserLoginDto getUserLoginDto() {
    return userLoginDto;
  }

  public void setUserLoginDto(UserLoginDto userLoginDto) {
    this.userLoginDto = userLoginDto;
  }
}
