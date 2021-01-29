package com.lawencon.elearning.controller;

import com.lawencon.elearning.dto.WebResponseDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.util.WebResponseUtils;
import javax.validation.ConstraintViolationException;
import org.postgresql.util.PSQLException;
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

  @ExceptionHandler(value = {ConstraintViolationException.class, DataIsNotExistsException.class,
      IllegalRequestException.class})
  public ResponseEntity<WebResponseDTO<String>> validationHandler(Exception e) {
    return WebResponseUtils.createWebResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {MissingServletRequestPartException.class})
  public ResponseEntity<WebResponseDTO<String>> missingRequestPart(
      MissingServletRequestPartException e) {
    return WebResponseUtils
        .createWebResponse(e.getRequestPartName() + " is not present in request form.",
            HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {PSQLException.class})
  public ResponseEntity<?> psqlError(
      PSQLException e) {
    if (e.getServerErrorMessage() != null) {
      String detailMessage = e.getServerErrorMessage().getDetail();
      if (detailMessage != null) {
        return WebResponseUtils.createWebResponse(detailMessage, HttpStatus.BAD_REQUEST);
      }
    }
    e.printStackTrace();
    return WebResponseUtils.createWebResponse("There is something error in internal server.",
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = {NullPointerException.class})
  public ResponseEntity<?> internalServerError(
      NullPointerException e) {
    e.printStackTrace();
    return WebResponseUtils
        .createWebResponse(e.getLocalizedMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
  }

}
