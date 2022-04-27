package app.openschool.common.services;

import app.openschool.auth.verification.VerificationToken;
import app.openschool.auth.verification.VerificationTokenRepository;
import app.openschool.user.User;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService implements CommunicationService {

  private final EmailSenderService emailSender;
  private final ITemplateEngine templateEngine;
  private final VerificationTokenRepository verificationTokenRepository;
  private final long expirationDuration;
  private final String verificationEndpoint;
  private final String emailSubject;

  public EmailService(
      EmailSenderService emailSender,
      ITemplateEngine templateEngine,
      VerificationTokenRepository verificationTokenRepository,
      @Value("${verification.duration}") long expirationDuration,
      @Value("${verification.endpoint}") String verificationEndpoint,
      @Value("${verification.subject}") String emailSubject) {
    this.emailSender = emailSender;
    this.templateEngine = templateEngine;
    this.verificationTokenRepository = verificationTokenRepository;
    this.expirationDuration = expirationDuration;
    this.verificationEndpoint = verificationEndpoint;
    this.emailSubject = emailSubject;
  }

  @Override
  @Transactional
  @Async
  public void sendEmailToVerifyUserAccount(User user) {
    String token = UUID.randomUUID().toString() + user.getId();
    long expiresAt = Instant.now().plus(expirationDuration, ChronoUnit.MINUTES).toEpochMilli();
    VerificationToken verificationToken = new VerificationToken(user, token, expiresAt);

    VerificationToken alreadyExistingToken =
        verificationTokenRepository.findVerificationTokenByUser(user);
    if (alreadyExistingToken != null) {
      verificationToken.setId(alreadyExistingToken.getId());
    }
    verificationTokenRepository.save(verificationToken);
    emailSender.sendEmail(
        user.getEmail(),
        createEmailContent(
            user.getName(),
            String.valueOf(expirationDuration),
            verificationEndpoint,
            verificationToken),
        emailSubject);
  }

  private String createEmailContent(
      String userName,
      String expiresAt,
      String verificationEndpoint,
      VerificationToken verificationToken) {
    Context context = new Context();
    context.setVariable("userName", userName);
    context.setVariable("verificationToken", verificationToken);
    context.setVariable("expiresAt", expiresAt);
    context.setVariable("verificationEndpoint", verificationEndpoint);
    return templateEngine.process("email/account-verification", context);
  }
}
