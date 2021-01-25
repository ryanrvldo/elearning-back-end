package com.lawencon.elearning.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Rian Rivaldo
 */
@Data
@AllArgsConstructor
public class WebResponseDTO<R> {

  private Integer code;
  private R result;

}
