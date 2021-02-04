package com.lawencon.elearning.util;

import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import com.lawencon.elearning.dto.EmailSetupDTO;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Component
public class MailUtils {

  @Autowired
  private JavaMailSender mailSender;

  public void sendPlainTextMail(EmailSetupDTO data) throws Exception {
    MimeMessage message = mailSender.createMimeMessage();

    MimeMessageHelper helper = new MimeMessageHelper(message, false);

    System.out.println("Sending email");
    helper.setTo(data.getTo());
    helper.setSubject(data.getSubject());
    helper.setText(data.getBody(), true);
    mailSender.send(message);
    System.out.println("Email Sent..");
  }


}
