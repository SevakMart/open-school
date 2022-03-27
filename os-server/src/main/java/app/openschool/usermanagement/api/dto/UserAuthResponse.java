package app.openschool.usermanagement.api.dto;

public class UserAuthResponse {

  private String token;
  private UserResponse userResponse;


  public UserAuthResponse() {
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public UserResponse getUserResponse() {
    return userResponse;
  }

  public void setUserResponse(UserResponse userResponse) {
    this.userResponse = userResponse;
  }

}
