package com.lawencon.elearning.dao;

import java.util.List;

public interface BaseCustomDao {

  default StringBuilder bBuilder(String... datas) {
    StringBuilder b = new StringBuilder();
    for (String d : datas) {
      b.append(d);
    }
    return b;
  }

  default <T> T getResultModel(List<T> obj) {
    return obj.size() > 0 ? obj.get(0) : null;
  }

}
