package app.openschool.common.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  private static final String AUTHORITIES = "authorities";

  private final long expirationTime;
  private final String secret;

  public JwtTokenProvider(
      @Value("${jwt.token-exp-time}") long expirationTime, @Value("${jwt.secret}") String secret) {
    this.expirationTime = expirationTime;
    this.secret = secret;
  }

  public String generateJwtToken(UserPrincipal userPrincipal) {
    return JWT.create()
        .withIssuedAt(new Date())
        .withSubject(userPrincipal.getUsername())
        .withArrayClaim(AUTHORITIES, getClaimsFromUser(userPrincipal))
        .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
        .sign(Algorithm.HMAC512(secret.getBytes(StandardCharsets.UTF_8)));
  }

  public boolean isTokenValid(String token) {
    JWTVerifier verifier = getJwtVerifier();
    return !isTokenExpired(verifier, token);
  }

  private String[] getClaimsFromToken(String token) {
    JWTVerifier verifier = getJwtVerifier();
    return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
  }

  public List<GrantedAuthority> getAuthorities(String token) {
    String[] claims = getClaimsFromToken(token);
    return Arrays.stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
  }

  private JWTVerifier getJwtVerifier() {
    return JWT.require(Algorithm.HMAC512(secret)).build();
  }

  private boolean isTokenExpired(JWTVerifier verifier, String token) {
    Date expiration = verifier.verify(token).getExpiresAt();
    return expiration.before(new Date());
  }

  public String getSubject(String token) {
    JWTVerifier verifier = getJwtVerifier();
    return verifier.verify(token).getSubject();
  }

  private String[] getClaimsFromUser(UserPrincipal userPrincipal) {
    return userPrincipal.getAuthorities().stream()
        .map(GrantedAuthority::getAuthority)
        .collect(Collectors.toList())
        .toArray(new String[0]);
  }

  public Authentication getAuthentication(
      String username, List<GrantedAuthority> authorities, HttpServletRequest request) {
    UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(username, null, authorities);
    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    return authenticationToken;
  }
}
