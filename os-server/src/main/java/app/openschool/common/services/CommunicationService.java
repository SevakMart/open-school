package app.openschool.common.services;

public interface CommunicationService {

  void sendResetPasswordEmail(String recipientEmail, String token);
}
