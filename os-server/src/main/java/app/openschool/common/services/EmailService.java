package app.openschool.common.services;

import app.openschool.auth.verification.VerificationToken;
import app.openschool.auth.verification.VerificationTokenRepository;
import app.openschool.user.User;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService implements CommunicationService {

  private final EmailSenderService emailSender;
  private final ITemplateEngine templateEngine;
  private final VerificationTokenRepository verificationTokenRepository;
  private final long expirationDuration;
  private final String verificationEndpoint;

  public EmailService(
      EmailSenderService emailSender,
      ITemplateEngine templateEngine,
      VerificationTokenRepository verificationTokenRepository,
      @Value("${verification.duration}") long expirationDuration,
      @Value("${verification.endpoint}") String verificationEndpoint) {
    this.emailSender = emailSender;
    this.templateEngine = templateEngine;
    this.verificationTokenRepository = verificationTokenRepository;
    this.expirationDuration = expirationDuration;
    this.verificationEndpoint = verificationEndpoint;
  }

  @Override
  @Async
  public void sendEmailToVerifyUserAccount(User user, TimeZone timeZone) {
    String token = UUID.randomUUID().toString() + user.getId();
    ZonedDateTime zonedDateTime = ZonedDateTime.now().plusDays(expirationDuration);
    long expiresAt = zonedDateTime.toInstant().toEpochMilli();
    String expiresAtDate =
        zonedDateTime
            .withZoneSameInstant(ZoneId.of(timeZone.getID()))
            .format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
    VerificationToken verificationToken = new VerificationToken(user, token, expiresAt);
    verificationTokenRepository.save(verificationToken);
    emailSender.sendEmail(
        user.getEmail(),
        createEmailContent(user.getName(), expiresAtDate, verificationEndpoint, verificationToken));
  }

  private String createEmailContent(
      String userName,
      String expiresAtDate,
      String verificationEndpoint,
      VerificationToken verificationToken) {
    Context context = new Context();
    context.setVariable("userName", userName);
    context.setVariable("verificationToken", verificationToken);
    context.setVariable("expiresAtDate", expiresAtDate);
    context.setVariable("verificationEndpoint", verificationEndpoint);
    return templateEngine.process("email/account-verification", context);
  }
}
