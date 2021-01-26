package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.service.DetailExamService;

/**
 * @author : Galih Dika Permana
 */
@RestController
@RequestMapping("/detailExam")
public class DetailExamController {

  @Autowired
  private DetailExamService dtlExamService;
}
