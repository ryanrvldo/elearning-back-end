package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.service.ScheduleService;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 *  @author Dzaky Fadhilla Guci
 */

@RestController
public class ScheduleController {

 @Autowired
 private ScheduleService scheduleService;

 @GetMapping("/schedules/teacher/{id}")
 public ResponseEntity<?> getSchedulesByModule(@PathVariable("id") String id) throws Exception {
   return WebResponseUtils.createWebResponse(scheduleService.getByTeacherId(id), HttpStatus.OK);
 }
 
}
