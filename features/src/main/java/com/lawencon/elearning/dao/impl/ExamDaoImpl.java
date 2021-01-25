package com.lawencon.elearning.dao.impl;

import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.ExamDao;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.model.ExamType;
import com.lawencon.util.Callback;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

/**
 * @author Dzaky Fadhilla Guci
 */

@Repository
public class ExamDaoImpl extends CustomBaseDao<Exam> implements ExamDao {

  @Override
  public List<Exam> getAllExams() throws Exception {
    return getAll();
  }

  @Override
  public void saveExam(Exam data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public Exam findExamById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public List<Exam> getExamsByModule(String id) throws Exception {
    String sql = buildQueryOf("SELECT id, trx_number , description , \"type\" ",
        ", start_time , end_time FROM tb_r_exams WHERE id_module = ?1 ").toString();
    
    List<Exam> listResult = new ArrayList<>();

    List<?> listObj = createNativeQuery(sql).setParameter(1, id).getResultList();

    listObj.forEach(val -> {
      Object[] objArr = (Object[]) val;
      Exam exam = new Exam();

      exam.setId((String) objArr[0]);
      exam.setTrxNumber((String) objArr[1]);
      exam.setDescription((String) objArr[2]);
      exam.setType(ExamType.valueOf((String) objArr[3]));

      Timestamp inDate = (Timestamp) objArr[4];
      exam.setStartTime((LocalDateTime) inDate.toLocalDateTime());

      inDate = (Timestamp) objArr[5];
      exam.setEndTime((LocalDateTime) inDate.toLocalDateTime());

      listResult.add(exam);

    });
    return listResult;
  }

  @Override
  public Long getCountData() throws Exception {
    String sql = buildQueryOf("SELECT count(*) as total_data FROM tb_r_exams").toString();
    return (Long) createNativeQuery(sql).getSingleResult();
  }

  @Override
  public Long getCountDataByModule(String id) throws Exception {
    String sql =
        buildQueryOf("SELECT count(*) as total_data FROM tb_r_exams WHERE id_module = ?1 ")
            .toString();
    return (Long) createNativeQuery(sql).setParameter(1, id).getSingleResult();
  }

  @Override
  public String getIdByCode(String code) throws Exception {
    String sql = buildQueryOf("SELECT id FROM tb_r_exams WHERE trx_number = ?1 ").toString();
    return (String) createNativeQuery(sql).setParameter(1, code).getSingleResult();
  }


}
