package app.openschool.auth.api.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import app.openschool.auth.api.annotation.processor.PasswordMatchValidator;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target(TYPE)
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordsMatch {
  String message() default "";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
