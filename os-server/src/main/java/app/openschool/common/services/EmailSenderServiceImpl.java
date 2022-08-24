package app.openschool.common.services;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Profile(value = {"local", "test"})
@Service
public class EmailSenderServiceImpl implements EmailSenderService {

  private final JavaMailSender mailSender;
  private final Logger logger = LoggerFactory.getLogger("EmailSenderService");
  private final String email;
  private final String personalEmailName;

  public EmailSenderServiceImpl(
      JavaMailSender mailSender,
      @Value("${spring.mail.username}") String email,
      @Value("${spring.mail.personal}") String personalEmailName) {
    this.mailSender = mailSender;
    this.email = email;
    this.personalEmailName = personalEmailName;
  }

  @Override
  public void sendEmail(String toEmail, String emailContent, String subject) {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
    try {
      helper.setSubject(subject);
      helper.setText(emailContent, true);
      helper.setTo(toEmail);
      helper.setFrom(email, personalEmailName);
      mailSender.send(message);
    } catch (MessagingException | UnsupportedEncodingException e) {
      logger.error("Sending mail to {} failed", toEmail);
    }
  }
}
