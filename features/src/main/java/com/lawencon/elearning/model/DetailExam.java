package com.lawencon.elearning.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.lawencon.model.BaseTransaction;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Galih Dika Permana
 *
 **/
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_r_dtl_exams")
public class DetailExam extends BaseTransaction {
  private static final long serialVersionUID = 1L;

  private Double grade = 0D;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_exam")
  private Exam exam;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_student")
  private Student student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_file")
  private File file;
}
