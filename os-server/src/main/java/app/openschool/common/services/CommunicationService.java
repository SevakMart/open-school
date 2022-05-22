package app.openschool.common.services;

import app.openschool.common.event.SendResetPasswordEmailEvent;
import app.openschool.common.event.SendVerificationEmailEvent;

public interface CommunicationService {

  void sendEmailToVerifyUserAccount(SendVerificationEmailEvent event);

  void sendResetPasswordEmail(SendResetPasswordEmailEvent event);
}
