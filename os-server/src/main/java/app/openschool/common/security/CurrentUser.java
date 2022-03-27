package app.openschool.common.security;

import app.openschool.usermanagement.entities.User;
import org.springframework.security.core.authority.AuthorityUtils;

public class CurrentUser extends org.springframework.security.core.userdetails.User {

  private User user;

  public CurrentUser(User user) {
    super(user.getEmail(), user.getPassword(),
        AuthorityUtils.createAuthorityList(user.getRole().getType()));
    this.user = user;
  }

  public User getUser() {
    return user;
  }
}
