package app.openschool.common.security;

import app.openschool.usermanagement.entity.User;
import app.openschool.usermanagement.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserDetailsServiceImp implements UserDetailsService {

  private final UserService userService;

  public CurrentUserDetailsServiceImp(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String email) {

    User user = userService.findUserByEmail(email);
    if (user == null) {
      throw new UsernameNotFoundException("Username not found");
    }
    return new CurrentUser(user);
  }
}
