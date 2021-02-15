package com.lawencon.elearning.dao.impl;

import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.GeneralDao;
import com.lawencon.elearning.model.General;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Repository
public class GeneralDaoImpl extends CustomBaseDao<General> implements GeneralDao {

  @Override
  public String getTemplateHTML(String code) throws Exception {

    String query = "SELECT template_html FROM tb_m_general WHERE code = ?1";
    return (String) createNativeQuery(query).setParameter(1, code).getSingleResult();
    
  }
}
