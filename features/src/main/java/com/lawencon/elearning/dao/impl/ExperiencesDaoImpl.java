package com.lawencon.elearning.dao.impl;

import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.ExperienceDao;
import com.lawencon.elearning.model.Experience;
import com.lawencon.elearning.util.HibernateUtils;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author Rian Rivaldo
 */
@Repository
public class ExperiencesDaoImpl extends CustomBaseDao<Experience> implements ExperienceDao {

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
    String query = buildQueryOf(
        "SELECT e.id as experience_id, e.title, e.description, e.start_date, e.end_date, e.version, e.id_teacher ",
        "FROM tb_m_experiences AS e ",
        "INNER JOIN tb_m_teachers t ON t.id = e.id_teacher ",
        "WHERE e.id_teacher = ?1");
    List<?> listObj = createNativeQuery(query)
        .setParameter(1, teacherId)
        .getResultList();
    return HibernateUtils.bMapperList(listObj, Experience.class, "id", "title", "description",
        "startDate", "endDate", "version", "teacher.id");
  }

  @Override
  public void update(Experience experience) throws Exception {
    save(experience, null, null, true, true);
  }

  @Override
  public void delete(String id) throws Exception {
    deleteById(id);
  }

}
