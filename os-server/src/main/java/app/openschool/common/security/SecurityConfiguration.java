package app.openschool.common.security;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import app.openschool.common.security.filters.JwtAuthenticationEntryPoint;
import app.openschool.common.security.filters.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@Profile({"local", "test"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final String[] PUBLIC_URLS = {
    "/static/**",
    "/index.html",
    "/",
    "/api/v1/auth/register",
    "/api/v1/auth/login",
    "/h2/**",
    "/open-school-api",
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/api/v1/auth/forgot-password",
    "/api/v1/auth/reset-password",
  };

  private final JwtAuthenticationFilter jwtAuthenticationFilter;
  private final UserDetailsService userDetailsService;
  private final BCryptPasswordEncoder passwordEncoder;
  private final JwtAuthenticationEntryPoint authenticationEntryPoint;

  public SecurityConfiguration(
      JwtAuthenticationFilter jwtAuthenticationFilter,
      UserDetailsService userDetailsService,
      BCryptPasswordEncoder passwordEncoder,
      JwtAuthenticationEntryPoint authenticationEntryPoint) {
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
    this.authenticationEntryPoint = authenticationEntryPoint;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers().frameOptions().sameOrigin();
    http.csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(STATELESS)
        .and()
        .authorizeRequests()
        .antMatchers(PUBLIC_URLS)
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint)
        .and()
        .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }
}
