package app.openschool.common.services.aws.email;

import app.openschool.auth.verification.VerificationToken;
import app.openschool.auth.verification.VerificationTokenRepository;
import app.openschool.auth.verification.api.dto.VerificationTokenDto;
import app.openschool.auth.verification.api.mapper.VerificationTokenMapper;
import app.openschool.common.event.SendResetPasswordEmailEvent;
import app.openschool.common.event.SendVerificationEmailEvent;
import app.openschool.common.services.EmailSenderServiceImpl;
import app.openschool.feature.api.CreateAwsTemplateRequest;
import app.openschool.user.User;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.ITemplateEngine;
import org.thymeleaf.context.Context;

@ConditionalOnMissingBean(value = {EmailSenderServiceImpl.class})
@Service
public class SesService implements AwsSesService {

  private final AwsSes awsSes;
  private final VerificationTokenRepository verificationTokenRepository;
  private final ITemplateEngine templateEngine;
  private final String resetPasswordEmailSubject;
  private final String emailSubject;
  private final String verificationEndpoint;
  private final long expirationDuration;

  public SesService(
      AwsSes awsSes,
      VerificationTokenRepository verificationTokenRepository,
      ITemplateEngine templateEngine,
      @Value("${verification.reset.password.email.subject}") String resetPasswordEmailSubject,
      @Value("${verification.subject}") String emailSubject,
      @Value("${verification.endpoint}") String verificationEndpoint,
      @Value("${verification.duration}") long expirationDuration) {
    this.awsSes = awsSes;
    this.verificationTokenRepository = verificationTokenRepository;
    this.templateEngine = templateEngine;
    this.resetPasswordEmailSubject = resetPasswordEmailSubject;
    this.emailSubject = emailSubject;
    this.verificationEndpoint = verificationEndpoint;
    this.expirationDuration = expirationDuration;
  }

  @Override
  @EventListener(classes = {SendResetPasswordEmailEvent.class})
  public void sendResetPasswordEmail(SendResetPasswordEmailEvent event) {
    awsSes.sendStandardEmail(
        List.of(event.getEmail()),
        createResetPasswordEmailContent(event.getToken()),
        resetPasswordEmailSubject);
  }

  @Override
  @Transactional
  @EventListener(SendVerificationEmailEvent.class)
  public void sendEmailToVerifyUserAccount(SendVerificationEmailEvent event) {
    User user = event.getUser();
    VerificationToken verificationToken = VerificationToken.generateVerificationToken(user);
    overwriteVerificationTokenIfAlreadyExist(user, verificationToken);
    awsSes.sendStandardEmail(
        List.of(user.getEmail()),
        createEmailContent(
            user.getName(),
            String.valueOf(expirationDuration),
            verificationEndpoint,
            VerificationTokenMapper.verificationTokenToVerificationTokenDto(verificationToken)),
        emailSubject);
  }

  @EventListener(CreateAwsTemplateRequest.class)
  @Override
  public void createTemplate(CreateAwsTemplateRequest request) {
    awsSes.createTemplate(
        request.getTemplateName(), request.getSubjectPart(), request.getHtmlPart());
  }

  @EventListener(String.class)
  @Override
  public void deleteTemplate(String templateName) {
    awsSes.deleteTemplate(templateName);
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
