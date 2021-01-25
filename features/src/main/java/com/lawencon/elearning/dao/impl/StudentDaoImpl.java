package com.lawencon.elearning.dao.impl;

import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.StudentDao;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author WILLIAM
 */
@Repository
public class StudentDaoImpl extends CustomBaseDao<Student> implements StudentDao {

  @Override
  public void insertStudent(Student data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public Student getStudentById(String id) {
    return getById(id);
  }

  @Override
  public Student getStudentProfile(String id) throws Exception {
    String query = buildQueryOf(
        "SELECT s.first_name, s.last_name, s.gender, s.created_at, u.email ",
        "FROM tb_m_students s INNER JOIN tb_m_users u ON u.id = s.id_user ", "WHERE s.id = ?");
    List<?> listObj = createNativeQuery(query).setParameter(1, id).getResultList();
    List<Student> listResult = HibernateUtils
        .bMapperList(listObj, Student.class, "firstName", "lastName", "gender", "createdAt",
            "user.email");
    return getResultModel(listResult);
  }

  @Override
  public void updateStudentProfile(Student data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public void deleteStudentById(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public void updateIsActiveById(Student data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public Student getStudentByIdUser(String id) throws Exception {
    String query = buildQueryOf(
        "SELECT s.id, u.first_name, u.last_name ",
        "FROM tb_m_students AS s ",
        "INNER JOIN tb_m_users as u",
        "WHERE id_user=?1 "
    );
    List<?> listObj = createNativeQuery(query).setParameter(1, id).getResultList();
    List<Student> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Student student = new Student();
      student.setId((String) objArr[0]);

      User user = new User();
      user.setFirstName((String) objArr[1]);
      user.setLastName((String) objArr[2]);
      student.setUser(user);
      listResult.add(student);
    });
    return getResultModel(listResult);
  }

}
