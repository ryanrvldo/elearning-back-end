package com.lawencon.elearning.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.elearning.dao.GeneralDao;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.service.GeneralService;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Service
public class GeneralServiceImpl implements GeneralService {

  @Autowired
  private GeneralDao generalDao;

  @Override
  public String getTemplateHTML(String code) throws Exception {

    return Optional.ofNullable(generalDao.getTemplateHTML(code))
        .orElseThrow(() -> new IllegalRequestException("code", code));
  }
}
