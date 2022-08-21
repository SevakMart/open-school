package app.openschool.common.configuration;

import app.openschool.common.services.aws.email.Ses;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@ConditionalOnBean(value = {Ses.class})
@Configuration
public class SesConfig {

  @Value("${cloud.aws.region.static}")
  private String region;

  @Bean
  public AmazonSimpleEmailService generateSesClient() {
    return AmazonSimpleEmailServiceClientBuilder.standard()
        .withCredentials(new InstanceProfileCredentialsProvider(false))
        .withRegion(region)
        .build();
  }
}
