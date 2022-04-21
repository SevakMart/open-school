package app.openschool.common.security.filters;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import app.openschool.auth.dto.UserLoginExceptionResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.Http403ForbiddenEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint extends Http403ForbiddenEntryPoint {

  @Override
  public void commence(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) {

    UserLoginExceptionResponse loginExceptionResponse =
        new UserLoginExceptionResponse(exception.getMessage());

    response.setContentType(APPLICATION_JSON_VALUE);
    response.setStatus(UNAUTHORIZED.value());

    try (OutputStream outputStream = response.getOutputStream()) {
      ObjectMapper mapper = new ObjectMapper();
      mapper.writeValue(outputStream, loginExceptionResponse);
      outputStream.flush();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
