package app.openschool.common.services;

import app.openschool.common.event.SendVerificationEmailEvent;

public interface CommunicationService {

  void sendEmailToVerifyUserAccount(SendVerificationEmailEvent event);

  void sendResetPasswordEmail(String recipientEmail, String token);
}
