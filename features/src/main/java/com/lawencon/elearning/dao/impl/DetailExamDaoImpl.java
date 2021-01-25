package com.lawencon.elearning.dao.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.DetailExamDao;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.util.HibernateUtils;

/**
 * @author : Galih Dika Permana
 */
@Repository
public class DetailExamDaoImpl extends CustomBaseDao<DetailExam> implements DetailExamDao {

  @Override
  public DetailExam getStudentScore(String id) throws Exception {
    String sql = buildQueryOf(
        "select AVG(de.grade), m.code, m.title, e.start_time, e.end_time ",
        "from tb_r_dtl_exams de",
        "inner join tb_r_exams e on e.id = de.id_exam ",
        "inner join tb_m_modules m on m.id = e.id_module where de.id_student = ?",
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
      exam.setStartTime((LocalDateTime) objArr[3]);
      exam.setEndTime((LocalDateTime) objArr[4]);
      exam.setModule(mdl);
      dtlExam.setExam(exam);
      listResult.add(dtlExam);
    });

    return getResultModel(listResult);
  }

  @Override
  public void updateIsActived(String id) throws Exception {
    String sql =
        buildQueryOf("UPDATE tb_r_dtl_exams SET is_active = FALSE WHERE id =?1 ").toString();
    createNativeQuery(sql).setParameter(1, id).executeUpdate();
  }

  @Override
  public void insertDetailExam(DetailExam dtlExam) throws Exception {
    save(dtlExam, null, null, true, true);
  }

  @Override
  public void updateDetailExam(DetailExam dtlExam) throws Exception {
    save(dtlExam, null, null, true, true);
  }

  @Override
  public void deleteDetailExam(String id) throws Exception {
    deleteById(id);
  }

  @Override
  public void updateScoreStudent(String id, Double score) throws Exception {
    String sql = buildQueryOf("UPDATE tb_r_dtl_exams SET grade = ?1 WHERE id = ?2").toString();
    createNativeQuery(sql).setParameter(1, score).setParameter(2, id).executeUpdate();
  }

  @Override
  public DetailExam getExamSubmission(String id) throws Exception {
    String sql =
        buildQueryOf(
            "SELECT de.id , de.trx_number AS code ,s.first_name ,s.last_name ,de.trx_date ",
            "FROM tb_r_dtl_exams de INNER JOIN tb_m_students s ON de.id_student =s.id ",
            "INNER JOIN tb_r_exams e ON de.id_exam =e.id WHERE de.id_exam = e.id").toString();
    List<DetailExam> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    listResult = HibernateUtils.bMapperList(listObj, DetailExam.class, "id", "trxNumber",
        "student.firstName", "student.lastName", "trxDate");
    //
    // listObj.forEach(val -> {
    // Object[] objArr = (Object[]) val;
    // DetailExam dtlExam = new DetailExam();
    // dtlExam.setId((String) objArr[0]);
    // dtlExam.setTrxNumber((String) objArr[1]);
    // Student student = new Student();
    // student.setFirstName((String) objArr[2]);
    // student.setLastName((String) objArr[3]);
    // dtlExam.setStudent(student);
    // dtlExam.setTrxDate((LocalDate) objArr[4]);
    // listResult.add(dtlExam);
    // });
    return getResultModel(listResult);
  }

  @Override
  public void sendStudentExam(DetailExam dtlExam) throws Exception {
    save(dtlExam, null, null, true, true);
  }

}
