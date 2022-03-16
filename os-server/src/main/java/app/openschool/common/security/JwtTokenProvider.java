package app.openschool.common.security;

import app.openschool.usermanagement.entities.UserPrincipal;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

  @Value("${jwt.token-exp-time}")
  private long expirationTime;

  @Value("${jwt.secret}")
  private String secret;

  private static final String AUTHORITIES = "authorities";
  private static final String TOKEN_CANNOT_BE_VERIFIED = "Token can not be verified";

  public String generateJwtToken(UserPrincipal userPrincipal) {
    return JWT.create()
        .withIssuedAt(new Date())
        .withSubject(userPrincipal.getUsername())
        .withArrayClaim(AUTHORITIES, getClaimsFromUser(userPrincipal))
        .withExpiresAt(new Date(System.currentTimeMillis() + expirationTime))
        .sign(Algorithm.HMAC512(secret.getBytes(StandardCharsets.UTF_8)));
  }

  // this method logic could be changed according further implementation
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
    JWTVerifier verifier;
    try {
      verifier = JWT.require(Algorithm.HMAC512(secret)).build();
    } catch (JWTVerificationException exception) {
      throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
    }
    return verifier;
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
    List<String> authorities = new ArrayList<>();
    for (GrantedAuthority grantedAuthority : userPrincipal.getAuthorities()) {
      authorities.add(grantedAuthority.getAuthority());
    }
    return authorities.toArray(new String[0]);
  }
}
