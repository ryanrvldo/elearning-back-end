package com.lawencon.elearning.model;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.lawencon.model.BaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author : Galih Dika Permana
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "student_course")
public class StudentCourse extends BaseMaster {

  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_student", nullable = false, foreignKey = @ForeignKey(name = "fk_student"))
  private Student studentId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_course", nullable = false, foreignKey = @ForeignKey(name = "fk_course"))
  private Course courseId;

  @Column(name = "is_verified", nullable = false)
  private Boolean isVerified = false;

  @Column(name = "verified_at")
  private LocalDateTime verifiedAt;

}
