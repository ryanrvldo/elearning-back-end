package com.lawencon.elearning.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.ExamDao;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.util.HibernateUtils;
import com.lawencon.util.Callback;

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
  public void updateExam(Exam data, Callback before) throws Exception {
    save(data, before, null, true, true);
  }

  @Override
  public Exam findExamById(String id) throws Exception {
    return getById(id);
  }

  @Override
  public List<Exam> getExamsByModule(String moduleId) throws Exception {
    String sql = buildQueryOf("SELECT id AS exam_id, trx_number , description , \"type\" , ",
        "start_time , end_time, id_file AS file_id, version FROM tb_r_exams WHERE id_module = ?1 ")
            .toString();
    
    List<?> listObj = createNativeQuery(sql).setParameter(1, moduleId).getResultList();

    List<Exam> listResult = HibernateUtils.bMapperList(listObj, Exam.class, "id", "trxNumber",
        "description",
        "type", "startTime", "endTime", "file.id", "version");

    return listResult.size() > 0 ? listResult : null;
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
    String sql =
        buildQueryOf("SELECT id FROM tb_r_exams WHERE trx_number = ?1 ")
            .toString();
    return (String) createNativeQuery(sql).setParameter(1, code).getSingleResult();
  }


}
