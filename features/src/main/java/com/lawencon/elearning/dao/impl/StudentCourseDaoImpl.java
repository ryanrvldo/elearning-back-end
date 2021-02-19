package com.lawencon.elearning.dao.impl;

import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.StudentCourseDao;
import com.lawencon.elearning.dto.StudentListByCourseResponseDTO;
import com.lawencon.elearning.model.StudentCourse;
import com.lawencon.util.Callback;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author : Galih Dika Permana
 */
@Repository
public class StudentCourseDaoImpl extends CustomBaseDao<StudentCourse> implements StudentCourseDao {

  @Override
  public void registerCourse(StudentCourse studentCourse, Callback before) throws Exception {
    save(studentCourse, before, null, true, true);
  }

  @Override
  public void verifyRegisterCourse(StudentCourse studentCourse, Callback before) throws Exception {
    save(studentCourse, before, null, true, true);
  }

  @Override
  public StudentCourse getStudentCourseById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public List<StudentListByCourseResponseDTO> getListStudentCourseById(String courseId)
      throws Exception {
    String sql = buildQueryOf(
        "SELECT s.id as student, s.code, u.first_name, u.last_name, u.email, s.phone, s.gender,sc.is_verified,sc.id as student_course ",
        "FROM student_course sc ", "INNER JOIN tb_m_students s ON s.id = sc.id_student ",
        "INNER JOIN tb_m_users u ON u.id = s.id_user ",
        "WHERE sc.id_course = ? ");
    List<?> listObj = createNativeQuery(sql).setParameter(1, courseId).getResultList();
    List<StudentListByCourseResponseDTO> listResult = new ArrayList<>();
    listObj.forEach(val -> {
      StudentListByCourseResponseDTO student = new StudentListByCourseResponseDTO();
      Object[] objArr = (Object[]) val;
      student.setStudentId((String) objArr[0]);
      student.setCode((String) objArr[1]);
      student.setFirstName((String) objArr[2]);
      student.setLastName((String) objArr[3]);
      student.setEmail((String) objArr[4]);
      student.setPhone((String) objArr[5]);
      student.setGender((String) objArr[6]);
      student.setIsVerified((Boolean) objArr[7]);
      student.setStudentCourseId((String) objArr[8]);
      listResult.add(student);
    });

    return listResult;
  }

  @Override
  public Boolean checkVerifiedCourse(String studentId, String courseId) throws Exception {
    String sql =
        buildQueryOf(
            "SELECT is_verified FROM student_course WHERE id_student = ?1 AND id_course = ?2 ");
    return ((Boolean) createNativeQuery(sql).setParameter(1, studentId).setParameter(2, courseId)
        .getSingleResult());
  }

}
