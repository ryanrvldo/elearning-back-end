package com.lawencon.elearning.dao.impl;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.DetailExamDao;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.model.User;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */
@Repository
public class DetailExamDaoImpl extends CustomBaseDao<DetailExam> implements DetailExamDao {

  @Override
  public List<DetailExam> getListScoreAvg(String id) throws Exception {
    String sql = buildQueryOf(
        "select AVG(de.grade), m.code, m.title, e.start_time, e.end_time ",
        "from tb_r_dtl_exams de ",
        "inner join tb_r_exams e on e.id = de.id_exam ",
        "inner join tb_m_modules m on m.id = e.id_module where de.id_student = ?1 ",
        "group by m.code, m.title, e.start_time, e.end_time").toString();
    List<DetailExam> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      DetailExam dtlExam = new DetailExam();
      dtlExam.setGrade((Double) objArr[0]);
      Module mdl = new Module();
      mdl.setCode((String) objArr[1]);
      mdl.setTitle((String) objArr[2]);
      Exam exam = new Exam();
      Timestamp inTime = (Timestamp) objArr[3];
      exam.setStartTime(inTime.toLocalDateTime());
      inTime = (Timestamp) objArr[4];
      exam.setEndTime(inTime.toLocalDateTime());
      exam.setModule(mdl);
      dtlExam.setExam(exam);
      listResult.add(dtlExam);
    });

    return listResult.size() > 0 ? listResult : null;
  }

  @Override
  public List<DetailExam> getListScoreReport(String id) throws Exception {
    String sql = buildQueryOf(
        "select de.grade , m.code, m.title,c.descripton ,c.code ,e.start_time, e.end_time ",
        "from tb_r_dtl_exams de inner join tb_r_exams e on e.id = de.id_exam ",
        "inner join tb_m_modules m on m.id = e.id_module ",
        "INNER JOIN tb_m_courses c ON m.id_course = c.id where de.id_student = ?1").toString();
    List<DetailExam> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      DetailExam dtlExam = new DetailExam();
      dtlExam.setGrade((Double) objArr[0]);
      Module mdl = new Module();
      mdl.setCode((String) objArr[1]);
      mdl.setTitle((String) objArr[2]);
      Course course = new Course();
      course.setDescription((String) objArr[3]);
      course.setCode((String) objArr[4]);
      Exam exam = new Exam();
      Timestamp inTime = (Timestamp) objArr[5];
      exam.setStartTime(inTime.toLocalDateTime());
      inTime = (Timestamp) objArr[5];
      exam.setEndTime(inTime.toLocalDateTime());
      exam.setModule(mdl);
      dtlExam.setExam(exam);
      listResult.add(dtlExam);
    });

    return listResult;
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    String sql =
        buildQueryOf("UPDATE tb_r_dtl_exams SET is_active = FALSE").toString();
    updateNativeSQL(sql, id, userId);
  }

  @Override
  public void insertDetailExam(DetailExam dtlExam, Callback before) throws Exception {
    save(dtlExam, before, null, true, true);
  }

  @Override
  public void updateDetailExam(DetailExam dtlExam, Callback before) throws Exception {
    save(dtlExam, before, null, true, true);
  }

  @Override
  public void deleteDetailExam(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public void updateScoreStudent(String id, Double score, String userId) throws Exception {
    String sql = buildQueryOf("UPDATE tb_r_dtl_exams SET grade = ?1").toString();
    updateNativeSQL(sql, id, userId, score.toString());
  }

  @Override
  public List<DetailExam> getExamSubmission(String id) throws Exception {
    String sql =
        buildQueryOf(
            "SELECT de.id , de.trx_number AS code ,u.first_name ,u.last_name ,de.grade,de.trx_date ",
            "FROM tb_r_dtl_exams de INNER JOIN tb_m_students s ON de.id_student =s.id ",
            "INNER JOIN tb_m_users u ON s.id_user = u.id WHERE de.id_exam = ?1").toString();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    List<DetailExam> listResult = new ArrayList<>();

    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      DetailExam dtlExam = new DetailExam();
      dtlExam.setId((String) objArr[0]);
      dtlExam.setTrxNumber((String) objArr[1]);

      User user = new User();
      user.setFirstName((String) objArr[2]);
      user.setLastName((String) objArr[3]);

      Student student = new Student();
      student.setUser(user);

      dtlExam.setGrade((Double) objArr[4]);
      Date inDate = (Date) objArr[5];
      dtlExam.setTrxDate(inDate.toLocalDate());
      dtlExam.setStudent(student);

      listResult.add(dtlExam);
    });
    return listResult;
  }

  @Override
  public void sendStudentExam(DetailExam dtlExam) throws Exception {
    save(dtlExam, null, null, true, true);
  }

  @Override
  public DetailExam getDetailById(String id) throws Exception {
    return getById(id);
  }

}
