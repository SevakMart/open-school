package app.openschool.common.services;

public interface EmailSenderService {
  void sendEmail(String toEmail, String emailContent);
}
