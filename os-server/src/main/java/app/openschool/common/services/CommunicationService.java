package app.openschool.common.services;

import app.openschool.common.event.SendVerificationEmailEvent;

import app.openschool.common.event.SendResetPasswordEmailEvent;

public interface CommunicationService {

  void sendEmailToVerifyUserAccount(SendVerificationEmailEvent event);

  void sendResetPasswordEmail(String recipientEmail, String token);
  void sendResetPasswordEmail(SendResetPasswordEmailEvent event);
}
