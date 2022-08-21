package app.openschool.common.services.aws;

import com.amazonaws.services.simpleemail.model.CreateTemplateResult;
import java.util.List;

public interface AwsSes {

  void sendStandardEmail(List<String> recipients, String htmlBody, String subject);

  void sendTemplatedEmail(List<String> toAddresses, String templateName, String templateData);

  CreateTemplateResult createTemplate(String templateName, String subjectPart, String htmlPart);

  void deleteTemplate(String templateName);
}
