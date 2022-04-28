package app.openschool.common.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

  private final JavaMailSender mailSender;
  private final String email;
  private final Logger logger = LoggerFactory.getLogger("EmailSenderService");

  public EmailSenderServiceImpl(
      JavaMailSender mailSender, @Value("${email.username}") String email) {
    this.mailSender = mailSender;
    this.email = email;
  }

  @Override
  public void sendEmail(String toEmail, String emailContent, String subject) {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
    try {
      helper.setSubject(subject);
      helper.setText(emailContent, true);
      helper.setFrom(email);
      helper.setTo(toEmail);
      mailSender.send(message);
    } catch (MessagingException e) {
      logger.error("Sending mail to {} failed", toEmail);
    }
  }
}
