package com.lawencon.elearning.service.impl;

import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.service.TeacherService;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Service
public class TeacherServiceImpl extends BaseServiceImpl implements TeacherService {

  @Override
  public void softDelete(String id) throws Exception {
    // check data dipake apa ngga
    // kalo ngga deletebyId dao
    // kalo iya => softdelete dao

    try {
      begin();
      // dao
      commit();

    } catch (Exception e) {
      e.printStackTrace();
      rollback();
    }


  }

}
