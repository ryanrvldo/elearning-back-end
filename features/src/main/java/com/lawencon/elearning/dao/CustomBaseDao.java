package com.lawencon.elearning.dao;

import java.io.Serializable;
import java.util.List;
import com.lawencon.base.BaseDaoImpl;

/**
 *  @author Dzaky Fadhilla Guci
 */

public class CustomBaseDao<T extends Serializable> extends BaseDaoImpl<T> {


  protected StringBuilder bBuilder(String... datas) {
    StringBuilder b = new StringBuilder();
    for (String d : datas) {
      b.append(d);
    }
    return b;
  }

  protected <T> T getResultModel(List<T> obj) {
    return obj.size() > 0 ? obj.get(0) : null;
  }


}
