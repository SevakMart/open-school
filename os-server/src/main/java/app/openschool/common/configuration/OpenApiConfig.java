package app.openschool.common.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
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
@SecurityScheme(
    name = "bearerAuth",
    type = SecuritySchemeType.HTTP,
    bearerFormat = "JWT",
    scheme = "bearer")
public class OpenApiConfig {}
