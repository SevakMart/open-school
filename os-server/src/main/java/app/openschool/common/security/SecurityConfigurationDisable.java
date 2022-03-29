package app.openschool.common.security;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@Profile("openapi")
public class SecurityConfigurationDisable extends WebSecurityConfigurerAdapter {

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers("/**");
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.cors ().configurationSource (request -> {
      CorsConfiguration cors = new CorsConfiguration ();
      cors.setAllowedOrigins (List.of ("http://localhost:3000"));
      return cors;
    });
  }
}
