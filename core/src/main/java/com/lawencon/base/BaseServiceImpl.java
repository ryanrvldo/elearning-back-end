package com.lawencon.base;

import com.lawencon.model.BaseEntity;
import com.lawencon.util.ThrowableSupplier;
import java.time.LocalDateTime;

/**
 * @author lawencon05
 */
public class BaseServiceImpl {

  protected void begin() {
    ConnHandler.begin();
  }

  protected void commit() {
    ConnHandler.commit();
  }

  protected void rollback() {
    ConnHandler.rollback();
  }

  protected void clear() {
    ConnHandler.clear();
  }

  protected <T extends BaseEntity> void setupUpdatedValue(T newData, ThrowableSupplier<T> supplier)
      throws Exception {
    T prevData = supplier.get();
    newData.setId(prevData.getId());
    newData.setCreatedAt(prevData.getCreatedAt());
    newData.setCreatedBy(prevData.getCreatedBy());
    newData.setVersion(prevData.getVersion());
    newData.setUpdatedAt(LocalDateTime.now());
  }
}
