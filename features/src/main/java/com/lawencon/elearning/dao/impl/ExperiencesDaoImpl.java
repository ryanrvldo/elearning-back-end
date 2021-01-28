package com.lawencon.elearning.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.ExperienceDao;
import com.lawencon.elearning.model.Experience;

/**
 * @author Rian Rivaldo
 */
@Repository
public class ExperiencesDaoImpl extends BaseDaoImpl<Experience> implements ExperienceDao {

  @Override
  public void create(Experience experience) throws Exception {
    save(experience, null, null, true, true);
  }

  @Override
  public Experience findById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public Experience findByCode(String code) throws Exception {
    return createQuery("FROM Experience WHERE code = ?1 ", Experience.class)
        .setParameter(1, code)
        .getSingleResult();
  }

  @Override
  public List<Experience> findAllByTeacherId(String teacherId) throws Exception {
    return createQuery("FROM Experience WHERE teacher.id = ?1 ", Experience.class)
        .setParameter(1, teacherId)
        .getResultList();
  }

  @Override
  public void update(Experience experience) throws Exception {
    save(experience, null, null, true, true);
  }

}
