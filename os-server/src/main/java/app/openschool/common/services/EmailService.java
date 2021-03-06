package app.openschool.common.services;

import app.openschool.auth.verification.VerificationToken;
import app.openschool.auth.verification.VerificationTokenRepository;
import app.openschool.auth.verification.api.dto.VerificationTokenDto;
import app.openschool.auth.verification.api.mapper.VerificationTokenMapper;
import app.openschool.common.event.SendResetPasswordEmailEvent;
import app.openschool.common.event.SendVerificationEmailEvent;
import app.openschool.user.User;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService implements CommunicationService {
  private final EmailSenderService emailSender;
  private final ITemplateEngine templateEngine;
  private final String resetPasswordEmailSubject;
  private final VerificationTokenRepository verificationTokenRepository;
  private final long expirationDuration;
  private final String verificationEndpoint;
  private final String emailSubject;

  public EmailService(
      EmailSenderService emailSender,
      ITemplateEngine templateEngine,
      @Value("${verification.reset.password.email.subject}") String resetPasswordEmailSubject,
      VerificationTokenRepository verificationTokenRepository,
      @Value("${verification.duration}") long expirationDuration,
      @Value("${verification.endpoint}") String verificationEndpoint,
      @Value("${verification.subject}") String emailSubject) {
    this.emailSender = emailSender;
    this.templateEngine = templateEngine;
    this.resetPasswordEmailSubject = resetPasswordEmailSubject;
    this.verificationTokenRepository = verificationTokenRepository;
    this.expirationDuration = expirationDuration;
    this.verificationEndpoint = verificationEndpoint;
    this.emailSubject = emailSubject;
  }

  @Override
  @Async
  @EventListener(classes = {SendResetPasswordEmailEvent.class})
  public void sendResetPasswordEmail(SendResetPasswordEmailEvent event) {
    emailSender.sendEmail(
        event.getEmail(),
        createResetPasswordEmailContent(event.getToken()),
        resetPasswordEmailSubject);
  }

  @Override
  @Transactional
  @Async
  @EventListener(SendVerificationEmailEvent.class)
  public void sendEmailToVerifyUserAccount(SendVerificationEmailEvent event) {
    User user = event.getUser();
    VerificationToken verificationToken = VerificationToken.generateVerificationToken(user);

    overwriteVerificationTokenIfAlreadyExist(user, verificationToken);

    emailSender.sendEmail(
        user.getEmail(),
        createEmailContent(
            user.getName(),
            String.valueOf(expirationDuration),
            verificationEndpoint,
            VerificationTokenMapper.verificationTokenToVerificationTokenDto(verificationToken)),
        emailSubject);
  }

  private String createResetPasswordEmailContent(String resetPasswordToken) {
    Context context = new Context();
    context.setVariable("resetPasswordToken", resetPasswordToken);
    return templateEngine.process("resetPassword", context);
  }

  private void overwriteVerificationTokenIfAlreadyExist(
      User user, VerificationToken verificationToken) {
    Optional<VerificationToken> alreadyExistingToken =
        verificationTokenRepository.findVerificationTokenByUser(user);
    alreadyExistingToken.ifPresent(token -> verificationToken.setId(token.getId()));
    verificationTokenRepository.save(verificationToken);
  }

  private String createEmailContent(
      String userName,
      String expiresAt,
      String verificationEndpoint,
      VerificationTokenDto verificationTokenDto) {
    Context context = new Context();
    context.setVariable("userName", userName);
    context.setVariable("verificationTokenDto", verificationTokenDto);
    context.setVariable("expiresAt", expiresAt);
    context.setVariable("verificationEndpoint", verificationEndpoint);
    return templateEngine.process("email/account-verification", context);
  }
}
