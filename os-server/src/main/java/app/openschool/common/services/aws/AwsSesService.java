package app.openschool.common.services.aws;

import app.openschool.common.event.SendResetPasswordEmailEvent;
import app.openschool.common.event.SendVerificationEmailEvent;
import app.openschool.feature.api.CreateAwsTemplateRequest;

public interface AwsSesService {

  void sendResetPasswordEmail(SendResetPasswordEmailEvent event);

  void sendEmailToVerifyUserAccount(SendVerificationEmailEvent event);

  void createTemplate(CreateAwsTemplateRequest request);

  void deleteTemplate(String templateName);
}
