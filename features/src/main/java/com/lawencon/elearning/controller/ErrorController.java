package com.lawencon.elearning.controller;

import com.lawencon.elearning.dto.WebResponseDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.util.WebResponseUtils;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

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

  @ExceptionHandler(value = {DataIsNotExistsException.class})
  public ResponseEntity<WebResponseDTO<String>> notFound(DataIsNotExistsException e) {
    return WebResponseUtils.createWebResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {MissingServletRequestPartException.class})
  public ResponseEntity<WebResponseDTO<String>> missingRequestPart(
      MissingServletRequestPartException e) {
    return WebResponseUtils
        .createWebResponse(e.getRequestPartName() + " is not present in request form.",
            HttpStatus.BAD_REQUEST);
  }

}
