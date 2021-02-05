package com.lawencon.elearning.dao.impl;

import java.util.List;
import org.springframework.stereotype.Repository;
import com.lawencon.elearning.dao.CustomBaseDao;
import com.lawencon.elearning.dao.ExamDao;
import com.lawencon.elearning.dto.exam.ExamsModuleResponseDTO;
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
    save(data, before, null, false, false);
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
  public List<ExamsModuleResponseDTO> getExamsByModule(String moduleId) throws Exception {
    String sql2 = buildQueryOf("SELECT tre.id AS exam_id, tre.exam_title , tre.trx_number , ",
        "tre.description , tre.exam_type , tre.start_time , ",
        "tre.end_time, tre.id_file AS file_id, tre.VERSION, ",
        "trf.\"name\" AS file_name FROM tb_r_exams tre ",
        "INNER JOIN tb_r_files trf ON trf.id =tre.id_file WHERE ",
        "id_module = ?1").toString();
    
    List<?> listObj = createNativeQuery(sql2).setParameter(1, moduleId).getResultList();

    List<ExamsModuleResponseDTO> listResult =
        HibernateUtils.bMapperList(listObj, ExamsModuleResponseDTO.class, "id", "title", "code",
        "description",
            "type", "startTime", "endTime", "fileId", "version", "fileName");

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
