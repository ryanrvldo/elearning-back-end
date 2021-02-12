package com.lawencon.elearning.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.lawencon.elearning.dto.TokenResponseDto;
import com.lawencon.elearning.dto.WebResponseDTO;

/**
 * @author Rian Rivaldo
 */
public class WebResponseUtils {

  public static <R> ResponseEntity<WebResponseDTO<R>> createWebResponse(R result,
      HttpStatus status) {
    WebResponseDTO<R> webResponse = new WebResponseDTO<>(status.value(), result);
    return new ResponseEntity<>(webResponse, status);
  }

  public static WebResponseDTO<TokenResponseDto> createSuccessAuthResponse(
      TokenResponseDto tokenResponse) {
    return new WebResponseDTO<>(HttpStatus.OK.value(), tokenResponse);
  }

  public static WebResponseDTO<String> createFailedAuthResponse(String message) {
    return new WebResponseDTO<>(HttpStatus.UNAUTHORIZED.value(), message);
  }

}
