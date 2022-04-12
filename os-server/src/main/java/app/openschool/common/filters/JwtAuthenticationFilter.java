package app.openschool.common.filters;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import app.openschool.common.security.JwtTokenProvider;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private static final String TOKEN_PREFIX = "Bearer ";

  private final JwtTokenProvider jwtTokenProvider;

  public JwtAuthenticationFilter(JwtTokenProvider jwtTokenProvider) {
    this.jwtTokenProvider = jwtTokenProvider;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String authorizationHeader = request.getHeader(AUTHORIZATION);
    if (authorizationHeader == null || !authorizationHeader.startsWith(TOKEN_PREFIX)) {
      filterChain.doFilter(request, response);
      return;
    }
    String token = authorizationHeader.substring(TOKEN_PREFIX.length());
    String username = jwtTokenProvider.getSubject(token);
    if (jwtTokenProvider.isTokenValid(token)
        && SecurityContextHolder.getContext().getAuthentication() == null) {
      List<GrantedAuthority> authorities = jwtTokenProvider.getAuthorities(token);
      Authentication authentication =
          jwtTokenProvider.getAuthentication(username, authorities, request);
      SecurityContextHolder.getContext().setAuthentication(authentication);
    } else {
      SecurityContextHolder.clearContext();
    }
    filterChain.doFilter(request, response);
  }
}
