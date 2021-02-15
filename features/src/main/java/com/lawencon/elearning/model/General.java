package com.lawencon.elearning.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import com.lawencon.model.BaseMaster;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tb_m_general",
    uniqueConstraints = {@UniqueConstraint(name = "bk_general", columnNames = {"code"})})
public class General extends BaseMaster {

  private static final long serialVersionUID = 1L;

  @Column(nullable = false, length = 50)
  private String code;

  @Column(columnDefinition = "TEXT", name = "template_html")
  private String templateHTML;

}
