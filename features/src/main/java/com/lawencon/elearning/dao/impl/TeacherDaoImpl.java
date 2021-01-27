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
  public List<Teacher> allTeachersForAdmin() throws Exception {
    String sql = buildQueryOf("SELECT tmt.id , tmt.code , tmu.first_name , tmu.last_name , ",
        "tmt.phone , tmt.gender , tmu.username , tmt.version FROM tb_m_teachers tmt ",
        "INNER JOIN tb_m_users tmu ON tmt.id_user = tmu.id WHERE tmt.is_active = true ");

    List<Teacher> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).getResultList();

    listResult = HibernateUtils.bMapperList(listObj, Teacher.class, "id", "code", "user.firstName",
        "user.lastName", "phone", "gender", "user.username", "version");

    return listResult.size() > 0 ? listResult : null;
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
        "SELECT tmu.first_name , tmu.last_name , tmu.email , tmt.title_degree , tmt.created_at, tmt.gender, tmt.version ",
        "FROM tb_m_teachers tmt ",
        "INNER JOIN tb_m_users tmu ON tmt.id_user = tmu.id WHERE id = ?1 AND is_active = true")
            .toString();


    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    List<Teacher> listResult = HibernateUtils.bMapperList(listObj, Teacher.class, "user.firstName",
        "user.lastName", "user.email", "titleDegree", "createdAt", "gender", "version");

    return getResultModel(listResult);
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    String sql = "UPDATE tb_m_teachers SET is_active = FALSE";
    createNativeQuery(sql).setParameter(1, id).executeUpdate();
    updateNativeSQL(sql, id, userId);
  }

  @Override
  public void updateTeacher(Teacher data, Callback before) throws Exception {
    save(data, before, null, true, true);

  }

  @Override
  public Teacher findByUserId(String userId) throws Exception {
    String sql = buildQueryOf(
        "SELECT tmt.phone , tmt.gender ",
        "FROM tb_m_teachers tmt ",
        "WHERE id_user = ?1 AND is_active = true").toString();


    List<?> listObj = createNativeQuery(sql).setParameter(1, userId).getResultList();

    List<Teacher> listResult =
        HibernateUtils.bMapperList(listObj, Teacher.class, "phone", "gender");

    return getResultModel(listResult);
  }

  @Override
  public void deleteTeacherById(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public Teacher findByIdForCourse(String id) throws Exception {
    String sql = buildQueryOf("SELECT tmt.id AS teacher_id , tmu.first_name , ",
        "tmu.last_name , tmt.title_degree, tmt.version FROM tb_m_teachers tmt ",
        "INNER JOIN tb_m_users tmu ON tmu.id = tmt.id_user AND is_active = TRUE").toString();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    List<Teacher> listResult =
        HibernateUtils.bMapperList(listObj, Teacher.class, "id", "user.firstName", "user.lastName",
            "titleDegree", "version");

    return getResultModel(listResult);
  }

  @Override
  public Long checkConstraint(String id) throws Exception {
    String sql = buildQueryOf("SELECT id FROM tb_m_teachers tmt ",
        "WHERE tmt.id = ?1 AND ?2 IN ",
        "((SELECT id_teacher FROM tb_m_courses) , (SELECT id_teacher FROM tb_m_schedules) , ",
        "(SELECT id_teacher FROM tb_m_experiences)) LIMIT 1").toString();

    return (Long) createNativeQuery(sql).setParameter(1, id).setParameter(2, id).getSingleResult();
  }

}
