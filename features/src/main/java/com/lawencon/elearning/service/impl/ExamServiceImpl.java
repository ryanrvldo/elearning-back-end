package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ExamDao;
import com.lawencon.elearning.dto.FileResponseDto;
import com.lawencon.elearning.dto.exam.TeacherExamRequestDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.service.DetailExamService;
import com.lawencon.elearning.service.ExamService;
import com.lawencon.elearning.service.FileService;
import com.lawencon.elearning.util.ValidationUtil;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Service
public class ExamServiceImpl extends BaseServiceImpl implements ExamService {

  @Autowired
  private ExamDao examDao;

  @Autowired
  private FileService fileService;

  @Autowired
  private ValidationUtil validateUtil;

  @Autowired
  private DetailExamService dtlExamService;

  @Override
  public List<Exam> getAllExams() throws Exception {
    return Optional.ofNullable(examDao.getAllExams())
        .orElseThrow(
            () -> new DataIsNotExistsException("Exam is empty and has not been initialized."));
  }

  @Override
  public void saveExam(MultipartFile multiPartFile, String content, String body) throws Exception {
    if (content == null) {
      throw new IllegalRequestException("Content cannot be empty!");
    }
    if (body == null) {
      throw new IllegalRequestException("Teacher Exam data cannot be empty!");
    }
    FileResponseDto fileResponseDTO = fileService.createFile(multiPartFile, content);
    TeacherExamRequestDTO teacherExam =
        new ObjectMapper().readValue(body, TeacherExamRequestDTO.class);
    validateUtil.validate(teacherExam);

    Module module = new Module();
    module.setId(teacherExam.getModuleId());
    module.setVersion(teacherExam.getModuleVersion());
    
    Exam exam = new Exam();
    exam.setModule(module);
    exam.setDescription(teacherExam.getDescription());
    exam.setStartTime(teacherExam.getStartTime());
    exam.setEndTime(teacherExam.getEndTime());
    exam.setCreatedAt(LocalDateTime.now());
    exam.setCreatedBy(teacherExam.getCreatedBy());

    File file = new File();
    file.setId(fileResponseDTO.getId());
    exam.setFile(file);

    examDao.saveExam(exam, null);
  }

  @Override
  public void updateExam(Exam data) throws Exception {
    validateNullId(data.getId(), "Id");
    examDao.saveExam(data, null);
  }

  @Override
  public Exam findExamById(String id) throws Exception {
    validateNullId(id, "Id");
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException("id", id);
    }
    return Optional.ofNullable(examDao.findExamById(id)).orElseThrow(
        () -> new DataIsNotExistsException("id", id));
  }

  @Override
  public List<Exam> getExamsByModule(String moduleId) throws Exception {
    validateNullId(moduleId, "Id Module");
    return Optional.ofNullable(examDao.getExamsByModule(moduleId)).orElseThrow(
        () -> new DataIsNotExistsException("Exam is empty and has not been initialized."));
  }

  @Override
  public Long getCountData() throws Exception {
    return examDao.getCountData();
  }

  @Override
  public Long getCountDataByModule(String id) throws Exception {
    validateNullId(id, "id");
    return examDao.getCountDataByModule(id);
  }

  @Override
  public String getIdByCode(String code) throws Exception {
    validateNullId(code, "code");
    return examDao.getIdByCode(code);
  }

  @Override
  public List<DetailExam> getListScoreAvg(String studentId) throws Exception {
    validateNullId(studentId, "Student Id");
    return dtlExamService.getListScoreAvg(studentId);
  }

  @Override
  public List<DetailExam> getExamSubmissions(String examId) throws Exception {
    validateNullId(examId, "Exam Id");
    return dtlExamService.getExamSubmission(examId);
  }

  @Override
  public void submitAssignemt(MultipartFile multiPartFile, String content, String body)
      throws Exception {
    if (content == null) {
      throw new IllegalRequestException("Content cannot be empty!");
    }
    if (body == null) {
      throw new IllegalRequestException("Student Exam data cannot be empty!");
    }
    dtlExamService.sendStudentExam(multiPartFile, content, body);
  }

  @Override
  public void updateScoreAssignment(String id, Double newScore, String teacherId) throws Exception {
    validateNullId("id", id);
    if (newScore == null || newScore < 0) {
      throw new IllegalRequestException("Bad request with score! cannot be empty or less than 0");
    }
    validateNullId("Teacher Id", teacherId);

    dtlExamService.updateScoreStudent(id, newScore, teacherId);
  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

}
