package com.lawencon.elearning.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lawencon.elearning.dto.forum.ForumRequestDTO;
import com.lawencon.elearning.service.ForumService;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 * @author Dzaky Fadhilla Guci
 */

@RestController
@RequestMapping("/forum")
public class ForumController {


  @Autowired
  private ForumService forumService;

  @GetMapping("/module/{id}")
  public ResponseEntity<?> getModuleDiscussion(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(forumService.getByModuleId(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> saveForum(@RequestBody ForumRequestDTO data) throws Exception {
    forumService.saveForum(data);
    return WebResponseUtils.createWebResponse("Add post success!", HttpStatus.CREATED);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteDiscussion(@RequestParam("id") String id,
      @RequestParam("userId") String userId) throws Exception {
    forumService.deleteForum(id, userId);
    return WebResponseUtils.createWebResponse("Post has been removed", HttpStatus.OK);
  }
}
