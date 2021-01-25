package com.lawencon.elearning.dao;

import java.io.Serializable;
import java.util.List;
import com.lawencon.base.BaseDaoImpl;

/**
 * @author Dzaky Fadhilla Guci
 */

public class CustomBaseDao<T extends Serializable> extends BaseDaoImpl<T> {

  protected String buildQueryOf(String... queries) {
    StringBuilder b = new StringBuilder();
    for (String d : queries) {
      b.append(d);
    }
    return b.toString();
  }

  protected T getResultModel(List<T> obj) {
    return obj.size() > 0 ? obj.get(0) : null;
  }
}
