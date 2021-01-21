package com.lawencon.elearning.dao.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.BaseCustomDao;
import com.lawencon.elearning.dao.StudentDao;
import com.lawencon.elearning.model.Gender;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.User;
import com.lawencon.util.Callback;

/**
 * 
 * @author WILLIAM
 *
 */
@Repository
public class StudentDaoImpl extends BaseDaoImpl<Student> implements StudentDao, BaseCustomDao {

  @Override
  public void insertStudent(Student data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public Student getStudentById(String id) {
    return getById(id);
  }

  @Override
  public Student getStudentProfile(String id) {
    List<Student> listResult = new ArrayList<>();
    String query = bBuilder("SELECT s.first_name, s.last_name, s.gender, s.created_at, u.email ",
        "FROM tb_m_students s INNER JOIN tb_m_users u ON u.id = s.id_user ", "WHERE s.id = ?")
            .toString();
    List<?> listObj = createNativeQuery(query).setParameter(1, id).getResultList();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Student student = new Student();
      student.setFirstName((String) objArr[0]);
      student.setLastName((String) objArr[1]);
      student.setGender((Gender) objArr[2]);
      student.setCreatedAt((LocalDateTime) objArr[3]);
      User user = new User();
      user.setEmail((String) objArr[4]);
      student.setUser(user);
      listResult.add(student);
    });
    return listResult.get(0);
  }

  @Override
  public void updateStudentProfile(Student data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

}
