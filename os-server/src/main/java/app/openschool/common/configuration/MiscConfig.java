package app.openschool.common.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

@Configuration
public class MiscConfig {
  @Bean
  public MethodValidationPostProcessor methodValidationPostProcessor() {
    MethodValidationPostProcessor methodValidationPostProcessor =
        new MethodValidationPostProcessor();
    return methodValidationPostProcessor;
  }
}
