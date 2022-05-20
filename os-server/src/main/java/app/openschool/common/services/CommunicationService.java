package app.openschool.common.services;

import app.openschool.common.event.SendResetPasswordEmailEvent;

public interface CommunicationService {

  void sendResetPasswordEmail(SendResetPasswordEmailEvent event);
}
