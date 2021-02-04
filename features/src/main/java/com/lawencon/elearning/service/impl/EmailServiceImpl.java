package com.lawencon.elearning.service.impl;

import com.lawencon.elearning.dto.EmailSetupDTO;
import com.lawencon.elearning.util.MailUtils;

/**
 *  @author Dzaky Fadhilla Guci
 */

public class EmailServiceImpl extends Thread {

  private MailUtils mailUtil;
  private EmailSetupDTO data;

  public EmailServiceImpl(MailUtils mailUtil, EmailSetupDTO data) {
    this.mailUtil = mailUtil;
    this.data = data;
  }

  @Override
  public void run() {
    try {
      mailUtil.sendPlainTextMail(data);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
