package com.lawencon.elearning.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.StudentDao;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Gender;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

/**
 * @author WILLIAM
 */
@Repository
public class StudentDaoImpl extends CustomBaseDao<Student> implements StudentDao {

  @Override
  public void insertStudent(Student data, Callback before) throws Exception {
    save(data, before, null, false, false);
  }

  @Override
  public Student getStudentById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public Student getStudentProfile(String id) throws Exception {
    String query = buildQueryOf(
        "SELECT u.first_name, u.last_name, s.gender, s.created_at, u.email ",
        "FROM tb_m_students s INNER JOIN tb_m_users u ON u.id = s.id_user ", "WHERE s.id = ?");
    List<?> listObj = createNativeQuery(query).setParameter(1, id).getResultList();
    List<Student> listResult = HibernateUtils
        .bMapperList(listObj, Student.class, "user.firstName", "user.lastName", "gender",
            "createdAt",
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
  public void updateIsActive(String id, String userId) throws Exception {
    String query = "UPDATE tb_m_students SET is_active = false";
    updateNativeSQL(query, id, userId);
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

  @Override
  public Student getStudentDashboard(String id) throws Exception {
    String query = buildQueryOf(
        "SELECT s.id as id_student, f.id as id_file, u.first_name, u.last_name, u.email, s.phone, s.gender, s.created_at ",
        "FROM tb_m_students s ", "INNER JOIN tb_m_users u ON u.id = s.id_user ",
        "LEFT JOIN tb_r_files f ON f.id = u.id_photo ", "WHERE s.id = ?");
    List<?> listObj = createNativeQuery(query).setParameter(1, id).getResultList();
    List<Student> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Student std = new Student();
      std.setId((String) objArr[0]);
      File file = new File();
      file.setId((String) objArr[1]);
      User user = new User();
      user.setUserPhoto(file);
      user.setFirstName((String) objArr[2]);
      user.setLastName((String) objArr[3]);
      user.setEmail((String) objArr[4]);
      std.setUser(user);
      std.setPhone((String) objArr[5]);
      std.setGender((Gender.valueOf((String) objArr[6])));
      Timestamp created = (Timestamp) objArr[7];
      std.setCreatedAt(created.toLocalDateTime());
      listResult.add(std);
    });
    return getResultModel(listResult);
  }

}
