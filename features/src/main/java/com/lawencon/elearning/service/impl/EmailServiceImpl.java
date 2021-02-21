package com.lawencon.elearning.service.impl;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import com.lawencon.elearning.dto.EmailSetupDTO;
import com.lawencon.elearning.error.InternalServerErrorException;
import com.lawencon.elearning.service.EmailService;

/**
 * @author Dzaky Fadhilla Guci
 */
@Service
public class EmailServiceImpl implements EmailService {

  private final static Logger LOGGER = LoggerFactory.getLogger(EmailServiceImpl.class);

  @Autowired
  private JavaMailSender mailSender;

  @Async
  @Override
  public void send(EmailSetupDTO emailSetup) throws InternalServerErrorException {
    try {
      MimeMessage mimeMessage = mailSender.createMimeMessage();
      MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
      helper.setText(buildHtmlContent(emailSetup.getHeading(), emailSetup.getBody()), true);
      helper.setSubject(emailSetup.getSubject());
      helper.setTo(emailSetup.getReceiver());
      mailSender.send(mimeMessage);
      LOGGER.info("Email successfully sent to " + emailSetup.getReceiver());
    } catch (MessagingException e) {
      String message = "Failed to send email to " + emailSetup.getReceiver();
      LOGGER.error(message, e);
      throw new InternalServerErrorException(message);
    }
  }

  @Override
  public String buildHtmlContent(String heading, String body) {
    return "<!DOCTYPE html\n"
        + "  PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n"
        + "<html xmlns=\"http://www.w3.org/1999/xhtml\" lang=\"en-GB\">\n"
        + "<head>\n"
        + "  <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n"
        + "  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n"
        + "  <title>Registration Confirm | Lawerning</title>\n"
        + "  <style type=\"text/css\">\n"
        + "    a[x-apple-data-detectors] {\n"
        + "      color: inherit !important;\n"
        + "    }\n"
        + "    #content {\n"
        + "      color: #153643;\n"
        + "      font-family: Arial, sans-serif;\n"
        + "      font-size: 16px;\n"
        + "      line-height: 24px;\n"
        + "      padding: 20px 0 30px 0;\n"
        + "    }\n"
        + "    #footerContent {\n"
        + "      color: #ffffff;\n"
        + "      font-family: Arial, sans-serif;\n"
        + "      font-size: 14px;\n"
        + "    }\n"
        + "  </style>\n"
        + "</head>\n"
        + "<body style=\"margin: 0; padding: 0\">\n"
        + "  <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\">\n"
        + "    <tr>\n"
        + "      <td style=\"padding: 20px 0 30px 0\">\n"
        + "        <table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"600\"\n"
        + "          style=\"border-collapse: collapse; border: 1px solid #cccccc\">\n"
        + "          <tr>\n"
        + "            <td align=\"center\" bgcolor=\"#f9bc60\" style=\"padding: 40px 0 30px 0\">\n"
        + "              <img\n"
        + "                src=\"https://raw.githubusercontent.com/mochamadapri14/elearning-front-end/master/projects/lawerning/src/assets/images/logo.png\"\n"
        + "                alt=\"Logo\" width=\"400\" height=\"auto\" style=\"display: block\" />\n"
        + "            </td>\n"
        + "          </tr>\n"
        + "          <tr>\n"
        + "            <td bgcolor=\"#ffffff\" style=\"padding: 40px 30px 40px 30px\">\n"
        + "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse\">\n"
        + "                <tr>\n"
        + "                  <td style=\"color: #153643; font-family: Arial, sans-serif\">\n"
        + String.format("<h1 style=\"font-size: 24px; margin: 0\">%s</h1>\n", heading)
        + "                  </td>\n"
        + "                </tr>\n"
        + "                <tr>\n"
        + "                  <td id=\"content\">\n"
        + "                    <p style=\"margin: 0\">\n"
        + String.format("%s\n", body)
        + "                      <br><br><br>Regards,<br>Lawerning Team.\n"
        + "                    </p>\n"
        + "                  </td>\n"
        + "                </tr>\n"
        + "                <tr>\n"
        + "                  <td></td>\n"
        + "                </tr>\n"
        + "              </table>\n"
        + "            </td>\n"
        + "          </tr>\n"
        + "          <tr>\n"
        + "            <td bgcolor=\"#232946\" style=\"padding: 30px 30px\">\n"
        + "              <table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse\">\n"
        + "                <tr>\n"
        + "                  <td id=\"footerContent\"><p style=\"margin: 0\">&reg; Lawerning Internationale, 2021<br/></p></td>\n"
        + "                </tr>\n"
        + "              </table>\n"
        + "            </td>\n"
        + "          </tr>\n"
        + "        </table>\n"
        + "      </td>\n"
        + "    </tr>\n"
        + "  </table>\n"
        + "</body>\n"
        + "</html>";
  }

}
