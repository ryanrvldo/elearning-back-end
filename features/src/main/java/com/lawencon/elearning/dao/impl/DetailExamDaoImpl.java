package com.lawencon.elearning.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.DetailExamDao;
import com.lawencon.elearning.dto.exam.detail.SubmissionStudentResponseDTO;
import com.lawencon.elearning.dto.exam.detail.SubmissionsByExamResponseDTO;
import com.lawencon.elearning.model.Course;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */
@Repository
public class DetailExamDaoImpl extends CustomBaseDao<DetailExam> implements DetailExamDao {

  @Override
  public List<DetailExam> getListScoreAvg(String id) throws Exception {
    String sql = buildQueryOf("select AVG(de.grade), m.code, m.title, e.start_time, e.end_time ",
        "from tb_r_dtl_exams de ", "inner join tb_r_exams e on e.id = de.id_exam ",
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
      Module mdl = new Module();
      Course course = new Course();
      Exam exam = new Exam();

      dtlExam.setGrade((Double) objArr[0]);
      mdl.setCode((String) objArr[1]);
      mdl.setTitle((String) objArr[2]);
      course.setDescription((String) objArr[3]);
      course.setCode((String) objArr[4]);
      Timestamp inTime = (Timestamp) objArr[5];
      exam.setStartTime(inTime.toLocalDateTime());
      inTime = (Timestamp) objArr[6];
      exam.setEndTime(inTime.toLocalDateTime());

      mdl.setCourse(course);
      exam.setModule(mdl);
      dtlExam.setExam(exam);
      listResult.add(dtlExam);
    });

    return listResult.size() > 0 ? listResult : null;
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    String sql = buildQueryOf("UPDATE tb_r_dtl_exams SET is_active = FALSE").toString();
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
  public void updateScoreStudent(DetailExam dtlExam, Callback before) throws Exception {
    save(dtlExam, before, null, true, true);

  }

  @Override
  public List<SubmissionsByExamResponseDTO> getExamSubmission(String id) throws Exception {
    String sql = buildQueryOf(
        "SELECT de.id AS submission_id , de.trx_number AS code ,u.first_name ,u.last_name ,de.grade,de.trx_date , trf.id AS file_id , trf.name ",
        "FROM tb_r_dtl_exams de INNER JOIN tb_m_students s ON de.id_student = s.id ",
        "INNER JOIN tb_m_users u ON s.id_user = u.id INNER JOIN tb_r_files trf on trf.id = de.id_file WHERE de.id_exam = ?1")
            .toString();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    List<SubmissionsByExamResponseDTO> listResult =
        HibernateUtils.bMapperList(listObj, SubmissionsByExamResponseDTO.class, "id", "code",
            "firstName", "lastName", "grade", "submittedDate", "fileId", "fileName");

    return listResult.size() > 0 ? listResult : null;
  }

  @Override
  public SubmissionStudentResponseDTO getStudentExamSubmission(String examId,
      String studentId) throws Exception {
    String sql = buildQueryOf(
        "SELECT de.id AS detail_id ,f.id AS file_id,f.\"name\",de.trx_number AS code ,u.first_name ,u.last_name ,de.grade,de.created_at ",
        "FROM tb_r_dtl_exams de INNER JOIN tb_m_students s ON de.id_student =s.id ",
        "LEFT JOIN tb_r_files f ON de.id_file = f.id ",
        "INNER JOIN tb_m_users u ON s.id_user = u.id WHERE de.id_exam = ?1 AND s.id = ?2");

    List<?> listObj =
        createNativeQuery(sql).setParameter(1, examId).setParameter(2, studentId).getResultList();

    SubmissionStudentResponseDTO submission = new SubmissionStudentResponseDTO();
    if (listObj.isEmpty()) {
      return null;
    }
    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      submission.setDetailId((String) objArr[0]);
      submission.setFileId((String) objArr[1]);
      submission.setFileName((String) objArr[2]);
      submission.setCode((String) objArr[3]);
      submission.setFirstName((String) objArr[4]);
      submission.setLastName((String) objArr[5]);
      submission.setGrade((Double) objArr[6]);
      submission.setSubmittedDate(((Timestamp) objArr[7]).toLocalDateTime());
    });

    return submission;
  }

  @Override
  public void sendStudentExam(DetailExam dtlExam) throws Exception {
    save(dtlExam, null, null, false, false);
  }

  @Override
  public DetailExam getDetailById(String id) throws Exception {
    return getById(id);
  }



}
