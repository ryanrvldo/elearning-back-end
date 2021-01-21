package com.lawencon.elearning.dao.impl;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.BaseCustomDao;
import com.lawencon.elearning.dao.TeacherDao;
import com.lawencon.elearning.model.Gender;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;
import com.lawencon.util.Callback;

@Repository
public class TeacherDaoImpl extends BaseDaoImpl<Teacher> implements TeacherDao, BaseCustomDao {

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

    String sql = bBuilder(
        "SELECT tmt.first_name , tmt.last_name , tmu.email , tmt.title_degree , tmt.created_at, tmt.gender ",
        "FROM tb_m_teachers tmt ",
        "INNER JOIN tb_m_users tmu ON tmt.id_user = tmu.id WHERE id = ?1").toString();

    List<Teacher> listResult = new ArrayList<>();
    List<?> listObj = em().createNativeQuery(sql).setParameter(1, id).getResultList();

    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Teacher teacher = new Teacher();
      teacher.setFirstName((String) objArr[0]);
      teacher.setLastName((String) objArr[1]);
      
      User user = new User();
      user.setEmail((String) objArr[2]);
     
      
      teacher.setTitleDegree((String) objArr[3]);
      Timestamp inDate = (Timestamp) objArr[4];
      teacher.setCreatedAt((LocalDateTime) inDate.toLocalDateTime());
      
      teacher.setGender(Gender.valueOf((String) objArr[5]));
      
      teacher.setUser(user);
      listResult.add(teacher);

    });
    return getResultModel(listResult);
  }

}
