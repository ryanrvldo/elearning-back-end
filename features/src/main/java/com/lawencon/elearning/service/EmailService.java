package com.lawencon.elearning.service;

import javax.mail.MessagingException;
import com.lawencon.elearning.dto.EmailSetupDTO;
import com.lawencon.elearning.error.InternalServerErrorException;

/**
 * @author Rian Rivaldo
 */
public interface EmailService {

  void send(EmailSetupDTO emailSetupDTO) throws MessagingException, InternalServerErrorException;

  String buildHtmlContent(String heading, String body);

}
