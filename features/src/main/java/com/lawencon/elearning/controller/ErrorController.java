package com.lawencon.elearning.controller;

import com.lawencon.elearning.dto.WebResponseDTO;
import com.lawencon.elearning.error.AttendanceErrorException;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.error.InternalServerErrorException;
import com.lawencon.elearning.util.WebResponseUtils;
import javax.validation.ConstraintViolationException;
import org.postgresql.util.PSQLException;
import org.postgresql.util.ServerErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

/**
 * @author Rian Rivaldo
 */
@RestControllerAdvice
public class ErrorController {

  @ExceptionHandler(value = {ConstraintViolationException.class, DataIsNotExistsException.class,
      IllegalRequestException.class, AttendanceErrorException.class})
  public ResponseEntity<WebResponseDTO<String>> validationHandler(Exception e) {
    e.printStackTrace();
    return WebResponseUtils.createWebResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {MissingServletRequestPartException.class,
      MissingServletRequestParameterException.class})
  public ResponseEntity<WebResponseDTO<String>> missingRequestPartHandler(
      Exception e) {
    e.printStackTrace();
    String message;
    if (e instanceof MissingServletRequestPartException) {
      message = ((MissingServletRequestPartException) e).getRequestPartName()
          + " is not present in request form.";
    } else {
      message = ((MissingServletRequestParameterException) e).getParameterName()
          + " is not present in query parameter.";
    }
    return WebResponseUtils.createWebResponse(message, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {PSQLException.class})
  public ResponseEntity<?> psqlHandler(PSQLException e) {
    e.printStackTrace();
    ServerErrorMessage serverErrorMessage = e.getServerErrorMessage();
    if (serverErrorMessage != null) {
      String detailMessage = serverErrorMessage.getDetail();
      if (detailMessage != null) {
        return WebResponseUtils.createWebResponse(detailMessage, HttpStatus.BAD_REQUEST);
      }
    }
    return WebResponseUtils.createWebResponse("There is something error in internal server.",
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = {NullPointerException.class, InternalServerErrorException.class})
  public ResponseEntity<?> internalServerHandler(Exception e) {
    e.printStackTrace();
    return WebResponseUtils.createWebResponse(
        "There is something error in internal server. Please try again later.",
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = {IllegalAccessException.class})
  public ResponseEntity<?> illegalAccessHandler(Exception e) {
    e.printStackTrace();
    return WebResponseUtils.createWebResponse(e.getMessage(), HttpStatus.FORBIDDEN);
  }

}
