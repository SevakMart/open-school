package app.openschool.common.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import app.openschool.auth.repository.ResetPasswordTokenRepository;
import app.openschool.auth.verification.VerificationTokenRepository;
import app.openschool.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.thymeleaf.ITemplateEngine;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {

  @Mock EmailSenderService emailSenderService;

  @Mock ITemplateEngine templateEngine;

  @Mock VerificationTokenRepository verificationTokenRepository;
  
  private CommunicationService communicationService;
  private String verificationEndpoint = "http://localhost:5000/api/v1/auth/account/verification";

  @BeforeEach
  void setUp() {
    communicationService =
        new EmailService(
            emailSenderService,
            templateEngine,
            "subject",
            verificationTokenRepository,
            15,
            verificationEndpoint,
            "Subject");
  }

  @Test
  void sendEmailToVerifyUserAccount() {
    communicationService.sendEmailToVerifyUserAccount(new User());
    verify(emailSenderService, times(1)).sendEmail(any(), any(), any());
  }
}
