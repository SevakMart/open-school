package app.openschool.common.communication;

import java.io.UnsupportedEncodingException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class Communication {

  @Autowired JavaMailSenderImpl mailSender;

  public void sendResetPasswordEmail(String recipientEmail, String token)
      throws MessagingException, UnsupportedEncodingException {
    MimeMessage message = mailSender.createMimeMessage();
    MimeMessageHelper helper = new MimeMessageHelper(message);
    helper.setFrom("epam.open.school@gmail.com", "Open School Support");
    helper.setTo(recipientEmail);
    String subject = "Here is the verification code to reset your password";
    String content =
        "<p>Hello,</p>"
            + "<p>You have requested to reset your password.</p>"
            + "<p>Here is the verification code:</p>"
            + "<p>"
            + token
            + "</p>"
            + "<br>"
            + "<p>Ignore this email if you remember your password, "
            + "or you have not made the request.</p>";
    helper.setSubject(subject);
    helper.setText(content, true);
    mailSender.send(message);
  }
}
