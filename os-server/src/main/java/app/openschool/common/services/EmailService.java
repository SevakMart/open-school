package app.openschool.common.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService implements CommunicationService {

  private final EmailSenderService emailSender;
  private final ITemplateEngine templateEngine;
  private final String resetPasswordEmailSubject;

  public EmailService(
      EmailSenderService emailSender,
      ITemplateEngine templateEngine,
      @Value("${verification.reset.password.email.subject}") String resetPasswordEmailSubject) {
    this.emailSender = emailSender;
    this.templateEngine = templateEngine;
    this.resetPasswordEmailSubject = resetPasswordEmailSubject;
  }

  @Override
  @Async
  public void sendResetPasswordEmail(String email, String resetPasswordToken) {
    emailSender.sendEmail(
        email, createResetPasswordEmailContent(resetPasswordToken), resetPasswordEmailSubject);
  }

  private String createResetPasswordEmailContent(String resetPasswordToken) {
    Context context = new Context();
    context.setVariable("resetPasswordToken", resetPasswordToken);
    return templateEngine.process("resetPassword", context);
  }
}