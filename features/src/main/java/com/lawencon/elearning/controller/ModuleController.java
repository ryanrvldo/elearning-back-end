package com.lawencon.elearning.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.lawencon.elearning.dto.UpdateIsActiveRequestDTO;
import com.lawencon.elearning.dto.file.FileResponseDto;
import com.lawencon.elearning.dto.module.ModulRequestDTO;
import com.lawencon.elearning.dto.module.UpdateModuleDTO;
import com.lawencon.elearning.service.ModuleService;
import com.lawencon.elearning.util.WebResponseUtils;

/**
 * @author : Galih Dika Permana
 */
@RestController
@RequestMapping("/module")
public class ModuleController {

  @Autowired
  private ModuleService moduleService;

  @GetMapping("/{id}")
  public ResponseEntity<?> getDetailModule(@PathVariable("id") String id) throws Exception {
    return WebResponseUtils.createWebResponse(moduleService.getModuleByIdCustom(id), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<?> insertModule(@RequestBody List<ModulRequestDTO> data) throws Exception {
    moduleService.insertModule(data);
    return WebResponseUtils.createWebResponse("Insert data success", HttpStatus.CREATED);
  }

  @DeleteMapping
  public ResponseEntity<?> deleteModule(@RequestBody UpdateIsActiveRequestDTO data) throws Exception {
    moduleService.deleteModule(data);
    return WebResponseUtils.createWebResponse("Delete data success", HttpStatus.OK);
  }

  @PatchMapping("/true")
  public ResponseEntity<?> updateIsActiveTrue(@RequestBody UpdateIsActiveRequestDTO data)
      throws Exception {
    moduleService.updateIsActiveTrue(data);
    return WebResponseUtils.createWebResponse("Set isActive module to true Success", HttpStatus.OK);
  }

  @PutMapping
  public ResponseEntity<?> updateModule(@RequestBody UpdateModuleDTO data) throws Exception {
    moduleService.updateModule(data);
    return WebResponseUtils.createWebResponse("Update data success", HttpStatus.OK);
  }
  
  @PostMapping("/lesson")
  public ResponseEntity<?> insertLesson(@RequestPart("file") List<MultipartFile> multiPartFiles,
      @RequestParam("idUser") String idUser, @RequestParam("idModule") String idModule)
      throws Exception {
    moduleService.saveLesson(multiPartFiles, idUser, idModule);
    return WebResponseUtils.createWebResponse("Insert lesson success", HttpStatus.OK);
  }
  
  @GetMapping("lesson/{idModule}")
  public ResponseEntity<?> getLesson(@PathVariable("idModule") String idModule) throws Exception {
    List<FileResponseDto> fileLesson = moduleService.getLessonFile(idModule);
    return WebResponseUtils.createWebResponse(fileLesson, HttpStatus.OK);
  }

}
