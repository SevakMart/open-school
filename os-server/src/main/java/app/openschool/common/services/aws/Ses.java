package app.openschool.common.services.aws;

import app.openschool.common.services.EmailSenderService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.CreateTemplateRequest;
import com.amazonaws.services.simpleemail.model.CreateTemplateResult;
import com.amazonaws.services.simpleemail.model.DeleteTemplateRequest;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;
import com.amazonaws.services.simpleemail.model.SendTemplatedEmailRequest;
import com.amazonaws.services.simpleemail.model.Template;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.stereotype.Service;

@ConditionalOnMissingBean(value = {EmailSenderService.class})
@Service
public class Ses implements AwsSes {

  private final String sender;

  private final AmazonSimpleEmailService sesClient;

  public Ses(@Value("${mail.username}") String sender, AmazonSimpleEmailService sesClient) {
    this.sender = sender;
    this.sesClient = sesClient;
  }

  @Override
  public void sendStandardEmail(List<String> recipients, String htmlBody, String subject) {
    SendEmailRequest request =
        new SendEmailRequest()
            .withDestination(new Destination().withToAddresses(recipients))
            .withMessage(
                new Message()
                    .withBody(
                        new Body().withHtml(new Content().withCharset("UTF-8").withData(htmlBody)))
                    .withSubject(new Content().withCharset("UTF-8").withData(subject)))
            .withSource(sender);
    sesClient.sendEmail(request);
  }

  @Override
  public void sendTemplatedEmail(
      List<String> toAddresses, String templateName, String templateData) {
    SendTemplatedEmailRequest request =
        new SendTemplatedEmailRequest()
            .withDestination(new Destination(toAddresses))
            .withTemplate(templateName)
            .withTemplateData(templateData)
            .withSource(sender);
    sesClient.sendTemplatedEmail(request);
  }

  @Override
  public CreateTemplateResult createTemplate(
      String templateName, String subjectPart, String htmlPart) {
    Template template = new Template();
    template.setTemplateName(templateName);
    template.setSubjectPart(subjectPart);
    template.setHtmlPart(htmlPart);
    CreateTemplateRequest createTemplateRequest = new CreateTemplateRequest();
    createTemplateRequest.setTemplate(template);
    return sesClient.createTemplate(createTemplateRequest);
  }

  @Override
  public void deleteTemplate(String templateName) {
    DeleteTemplateRequest request = new DeleteTemplateRequest();
    request.setTemplateName(templateName);
    sesClient.deleteTemplate(request);
  }
}
