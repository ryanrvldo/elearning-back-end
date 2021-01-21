package com.lawencon.elearning.model;

import java.time.LocalDate;
import java.time.LocalTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.lawencon.model.BaseMaster;
import lombok.Data;

/**
 * @author : Galih Dika Permana
 *
 **/
@Data
@Entity
@Table(name = "tb_m_schedules")
public class Schedule extends BaseMaster {
  private static final long serialVersionUID = 1L;

  @Column(unique = true, nullable = false, length = 50)
  private String code;

  @Column(name = "schedule_date", nullable = false)
  private LocalDate date;

  @Column(name = "start_time", nullable = false)
  private LocalTime startTime;

  @Column(name = "end_time", nullable = false)
  private LocalTime endTime;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_teacher", nullable = false)
  private Teacher teacher;
}
