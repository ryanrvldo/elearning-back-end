package com.lawencon.elearning.dao.impl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.TeacherDao;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

/**
 * @author Dzaky Fadhilla Guci
 */

@Repository
public class TeacherDaoImpl extends CustomBaseDao<Teacher> implements TeacherDao {

  @Override
  public List<Teacher> getAllTeachers() throws Exception {
    return getAll();
  }

  @Override
  public void saveTeacher(Teacher data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public Teacher findTeacherById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public Teacher findTeacherByIdCustom(String id) throws Exception {

    String sql = buildQueryOf(
        "SELECT tmt.first_name , tmt.last_name , tmu.email , tmt.title_degree , tmt.created_at, tmt.gender ",
        "FROM tb_m_teachers tmt ",
        "INNER JOIN tb_m_users tmu ON tmt.id_user = tmu.id WHERE id = ?1").toString();

    List<Teacher> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    listResult = HibernateUtils.bMapperList(listObj, Teacher.class, "firstName", "lastName",
        "user.email", "titleDegree", "createdAt", "gender");

    return getResultModel(listResult);
  }

  @Override
  public void updateIsActive(String id) throws Exception {
    String sql = "UPDATE tb_m_teachers SET is_active = FALSE WHERE id =?1";
    createNativeQuery(sql).setParameter(1, id).executeUpdate();
  }

  @Override
  public void updateTeacher(Teacher data, Callback before) throws Exception {
    save(data, before, null, true, true);

  }

  @Override
  public Teacher findByUserId(String userId) throws Exception {
    String sql = buildQueryOf(
        "SELECT tmt.first_name , tmt.last_name ",
        "FROM tb_m_teachers tmt ",
        "WHERE id_user = ?1").toString();

    List<Teacher> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).setParameter(1, userId).getResultList();

    listResult = HibernateUtils.bMapperList(listObj, Teacher.class, "firstName", "lastName");

    return getResultModel(listResult);
  }

  @Override
  public void deleteTeacherById(String id) throws Exception {
    deleteById(id);
  }

}
