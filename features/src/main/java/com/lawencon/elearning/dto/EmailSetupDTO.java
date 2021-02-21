package com.lawencon.elearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailSetupDTO {

  private String receiver;
  private String subject;
  private String heading;
  private String body;

}
