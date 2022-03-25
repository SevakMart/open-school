package app.openschool;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@OpenAPIDefinition(
    info =
        @Info(
            title = "Open-School API",
            version = "V1",
            description =
                "The API provides the ability to register for students, "
                    + "log in and out for students, mentors, and administrators, "
                    + "for authenticated students, check and enroll in courses, "
                    + "get information about courses, their categories, and mentors, "
                    + "for authenticated mentors to create courses, "
                    + "and for administrators to register mentors"))
public class OpenSchoolApplication {

  public static void main(String[] args) {

    SpringApplication.run(OpenSchoolApplication.class, args);
  }

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
