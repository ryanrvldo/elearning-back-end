package com.lawencon.elearning.controller;

import com.lawencon.elearning.dto.WebResponseDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.util.WebResponseUtils;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Rian Rivaldo
 */
@RestControllerAdvice
public class ErrorController {

  @ExceptionHandler(value = {ConstraintViolationException.class})
  public ResponseEntity<WebResponseDTO<String>> validationHandler(
      ConstraintViolationException exception) {
    return WebResponseUtils.createWebResponse(exception.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {RuntimeException.class, DataIsNotExistsException.class})
  public ResponseEntity<WebResponseDTO<String>> notFound(RuntimeException e) {
    return WebResponseUtils.createWebResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

}
