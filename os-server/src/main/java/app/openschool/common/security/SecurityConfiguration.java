package app.openschool.common.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@Profile({"local", "test"})
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  private static final String[] PUBLIC_URLS = {
    "/static/**",
    "/index.html",
    "/",
    "/api/v1/register",
    "/api/v1/login",
    "/h2/**",
    "/open-school-api",
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/api/v1/mentors",
    "/api/v1/categories",
  };

  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final JwtAuthenticationEntryPoint unauthorizedHandler;

  public SecurityConfiguration(JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,
                               UserDetailsService userDetailsService,
                               PasswordEncoder passwordEncoder) {
    this.unauthorizedHandler = jwtAuthenticationEntryPoint;
    this.userDetailsService = userDetailsService;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.headers().frameOptions().sameOrigin();
    http.csrf()
        .disable()
        .sessionManagement()
        .sessionCreationPolicy(STATELESS)
        .and()
        .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
        .and()
        .authorizeRequests()
        .antMatchers(PUBLIC_URLS)
        .permitAll()
        .anyRequest()
        .authenticated();
    http.addFilterBefore(authenticationTokenFilterBean(),
         UsernamePasswordAuthenticationFilter.class);
    http.headers().cacheControl();
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userDetailsService)
        .passwordEncoder(passwordEncoder);
  }

  @Bean
  public JwtAuthenticationTokenFilter authenticationTokenFilterBean() {
    return new JwtAuthenticationTokenFilter();
  }
}
