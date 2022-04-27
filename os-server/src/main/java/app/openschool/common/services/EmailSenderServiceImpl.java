package app.openschool.common.services;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderServiceImpl implements EmailSenderService {

  private final JavaMailSender mailSender;
  private final Logger logger = LoggerFactory.getLogger("EmailSenderService");

  public EmailSenderServiceImpl(JavaMailSender mailSender) {
    this.mailSender = mailSender;
  }

  @Override
  public void sendEmail(String toEmail, String emailContent, String subject) {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
    try {
      helper.setSubject(subject);
      helper.setText(emailContent, true);
      helper.setFrom("epam.open.school@gmail.com");
      helper.setTo(toEmail);
      mailSender.send(message);
    } catch (MessagingException e) {
      logger.error("Sending mail to " + toEmail + " failed");
    }
  }
}