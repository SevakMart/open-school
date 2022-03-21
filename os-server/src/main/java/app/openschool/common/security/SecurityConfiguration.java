package app.openschool.common.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final String[] PUBLIC_URLS = {
    "/static/**",
    "/**/favicon.ico",
    "/**/manifest.json",
    "/**/logo192.png",
    "/**/logo512.png",
    "/",
    "/api/v1/register",
  };

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(PUBLIC_URLS)
        .permitAll()
        .anyRequest()
        .authenticated();
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
