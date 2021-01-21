package com.lawencon.elearning.dao.impl;

import java.util.List;
import com.lawencon.base.BaseDaoImpl;
import com.lawencon.elearning.dao.BaseCustomDao;
import com.lawencon.elearning.dao.DetailExamDao;
import com.lawencon.elearning.model.DetailExam;

/**
 * @author : Galih Dika Permana
 */

public class DetailExamDaoImpl extends BaseDaoImpl<DetailExam>
    implements DetailExamDao, BaseCustomDao {

  @Override
  public DetailExam getStudentScore(String id) {
    DetailExam dtlExam = new DetailExam();
    String query =
        "select AVG(de.grade), m.code, m.title, e.start_time, e.end_time from tb_r_dtl_exams de"
            + "inner join tb_r_exams e on e.id = de.id_exam "
            + "inner join tb_m_modules m on m.id = e.id_module" + "where de.id_student = ?"
            + "group by m.code, m.title, e.start_time, e.end_time";
    List<Object> queryObj = createNativeQuery(query).setParameter(1, id).getResultList();

    return null;
  }

}
