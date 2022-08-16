package app.openschool.common.configuration;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {

  @Value("${cloud.aws.region.static}")
  private String region;

  @Bean
  public AmazonS3 generateAwsS3Client() {
    return AmazonS3ClientBuilder.standard()
        .withCredentials(new InstanceProfileCredentialsProvider(false))
        .withRegion(region)
        .build();
  }
}
