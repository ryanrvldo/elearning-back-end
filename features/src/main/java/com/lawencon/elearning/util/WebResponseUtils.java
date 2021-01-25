package com.lawencon.elearning.util;

import com.lawencon.elearning.dto.WebResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * @author Rian Rivaldo
 */
public class WebResponseUtils {

  public static <R> ResponseEntity<WebResponseDTO<R>> createSuccessResponse(R result,
      HttpStatus status) {
    WebResponseDTO<R> webResponse = new WebResponseDTO<>(status.value(), result);
    return new ResponseEntity<>(webResponse, status);
  }

}
