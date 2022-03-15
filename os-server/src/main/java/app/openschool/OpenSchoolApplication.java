package app.openschool;

import app.openschool.common.security.JwtTokenProvider;
import app.openschool.usermanagement.entities.RoleEntity;
import app.openschool.usermanagement.entities.UserEntity;
import app.openschool.usermanagement.entities.UserPrincipal;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/** Useful Javadoc. */
@SpringBootApplication
public class OpenSchoolApplication {

  public static void main(String[] args) {

    SpringApplication.run(OpenSchoolApplication.class, args);
  }
}
