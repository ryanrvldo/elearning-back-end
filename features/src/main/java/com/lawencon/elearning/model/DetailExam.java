package com.lawencon.elearning.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
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
@Table(name = "tb_r_dtl_exams",
    uniqueConstraints = {
        @UniqueConstraint(name = "fk_exam_student", columnNames = {"id_exam", "id_student"})})
public class DetailExam extends BaseTransaction {
  private static final long serialVersionUID = 1L;

  private Double grade = 0D;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_exam", nullable = false, foreignKey = @ForeignKey(name = "fk_exam"))
  private Exam exam;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_student", nullable = false, foreignKey = @ForeignKey(name = "fk_student"))
  private Student student;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_file", nullable = false, foreignKey = @ForeignKey(name = "fk_file"))
  private File file;

}
