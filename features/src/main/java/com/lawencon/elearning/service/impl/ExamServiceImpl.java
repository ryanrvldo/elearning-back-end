package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.ExamDao;
import com.lawencon.elearning.dto.EmailSetupDTO;
import com.lawencon.elearning.dto.exam.ExamsModuleResponseDTO;
import com.lawencon.elearning.dto.exam.TeacherExamRequestDTO;
import com.lawencon.elearning.dto.exam.UpdateScoreRequestDTO;
import com.lawencon.elearning.dto.exam.detail.ScoreAverageResponseDTO;
import com.lawencon.elearning.dto.exam.detail.ScoreReportDTO;
import com.lawencon.elearning.dto.exam.detail.SubmissionStudentResponseDTO;
import com.lawencon.elearning.dto.exam.detail.SubmissionsByExamResponseDTO;
import com.lawencon.elearning.dto.file.FileRequestDto;
import com.lawencon.elearning.dto.file.FileResponseDto;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.FileType;
import com.lawencon.elearning.model.GeneralCode;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.service.DetailExamService;
import com.lawencon.elearning.service.EmailService;
import com.lawencon.elearning.service.ExamService;
import com.lawencon.elearning.service.FileService;
import com.lawencon.elearning.service.GeneralService;
import com.lawencon.elearning.service.UserService;
import com.lawencon.elearning.util.TransactionNumberUtils;
import com.lawencon.elearning.util.ValidationUtil;

/**
 * @author Dzaky Fadhilla Guci
 */

@Service
public class ExamServiceImpl extends BaseServiceImpl implements ExamService {

  @Autowired
  private ExamDao examDao;

  @Autowired
  private FileService fileService;

  @Autowired
  private DetailExamService dtlExamService;

  @Autowired
  private GeneralService generalService;

  @Autowired
  private EmailService emailService;

  @Autowired
  private UserService userService;

  @Autowired
  private ValidationUtil validateUtil;

  @Autowired
  private ObjectMapper objectMapper;

  @Override
  public void saveExam(MultipartFile multiPartFile, String body) throws Exception {

    if (body == null && multiPartFile == null) {
      throw new IllegalRequestException("Exam data cannot be empty!");
    }
    objectMapper.registerModule(new JavaTimeModule());

    TeacherExamRequestDTO teacherExam;
    try {
      teacherExam = objectMapper.readValue(body, TeacherExamRequestDTO.class);
    } catch (Exception e) {
      throw new IllegalRequestException("Invalid create exam request.");
    }
    
    validateUtil.validate(teacherExam);
    if (teacherExam.getStartTime().compareTo(teacherExam.getEndTime()) > 0) {
      throw new IllegalRequestException("End time cannot be greater than start time");
    }

    FileRequestDto fileRequest = new FileRequestDto();
    fileRequest.setType(FileType.EXAM.toString());
    fileRequest.setUserId(teacherExam.getCreatedBy());

    Module module = new Module();
    module.setId(teacherExam.getModuleId());

    Exam exam = new Exam();
    exam.setModule(module);
    exam.setTitle(teacherExam.getTitle());
    exam.setDescription(teacherExam.getDescription());
    exam.setStartTime(teacherExam.getStartTime());
    exam.setEndTime(teacherExam.getEndTime());
    exam.setCreatedAt(LocalDateTime.now());
    exam.setCreatedBy(teacherExam.getCreatedBy());
    exam.setTrxDate(LocalDate.now());
    exam.setTrxNumber(TransactionNumberUtils.generateExamTrxNumber());
    exam.setExamType(teacherExam.getType());

    File file = new File();
    exam.setFile(file);

    try {
      begin();
      FileResponseDto fileResponseDTO =
          Optional.ofNullable(fileService.createFile(multiPartFile, fileRequest))
              .orElseThrow(() -> new DataIsNotExistsException("Failed to upload exam file."));
      file.setId(fileResponseDTO.getId());
      examDao.saveExam(exam, null);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
    List<String> emailList = userService.getEmailUsersPerModule(teacherExam.getModuleId());
    String template = generalService.getTemplateHTML(GeneralCode.TEACHER_EXAM.getCode());
    EmailSetupDTO emailSetup = new EmailSetupDTO();
    emailSetup.setSubject("New Exam Posted");
    emailSetup.setHeading("A new exam have been posted.");
    emailSetup.setBody(template);
    for (String email : emailList) {
      emailSetup.setReceiver(email);
      emailService.send(emailSetup);
    }
  }

  @Override
  public void updateExam(Exam data) throws Exception {
    validateUtil.validateUUID(data.getId());
    examDao.saveExam(data, null);
  }

  @Override
  public Exam findExamById(String id) throws Exception {
    validateUtil.validateUUID(id);
    return Optional.ofNullable(examDao.findExamById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public void deleteExam(String id) throws Exception {
    validateUtil.validateUUID(id);
    String idFile = Optional.ofNullable(examDao.getIdFileById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
    try {
      begin();
      examDao.deleteExam(id);
      fileService.deleteFile(idFile);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }

  }

  @Override
  public List<ExamsModuleResponseDTO> getExamsByModule(String moduleId) throws Exception {
    validateUtil.validateUUID(moduleId);
    return Optional.ofNullable(examDao.getExamsByModule(moduleId))
        .orElse(Collections.emptyList());
  }

  @Override
  public List<ScoreAverageResponseDTO> getListScoreAvg(String studentId) throws Exception {
    validateUtil.validateUUID(studentId);
    return dtlExamService.getListScoreAvg(studentId);
  }

  @Override
  public List<SubmissionsByExamResponseDTO> getExamSubmissions(String examId) throws Exception {
    validateUtil.validateUUID(examId);
    return dtlExamService.getExamSubmission(examId);
  }

  @Override
  public SubmissionStudentResponseDTO getStudentExamSubmission(String examId,
      String studentId) throws Exception {
    validateUtil.validateUUID(examId, studentId);
    return dtlExamService.getStudentExamSubmission(examId, studentId);
  }

  @Override
  public void submitAssignment(MultipartFile multiPartFile, String examId, String studentId)
      throws Exception {
    if (examId == null && studentId == null) {
      throw new IllegalRequestException("Student Exam data cannot be empty!");
    }
    Exam exam = findExamById(examId);
    if (exam.getEndTime().isBefore(LocalDateTime.now())) {
      throw new IllegalRequestException("time's up");
    }
    dtlExamService.sendStudentExam(multiPartFile, examId, studentId);
  }

  @Override
  public void updateScoreAssignment(UpdateScoreRequestDTO data) throws Exception {
    validateUtil.validate(data);
    dtlExamService.updateScoreStudent(data);
  }

  @Override
  public List<ScoreReportDTO> getListScoreReport(String id) throws Exception {
    validateUtil.validateUUID(id);
    return Optional.ofNullable(dtlExamService.getListScoreReport(id))
        .orElse(Collections.emptyList());
  }

  @Override
  public void deleteExamSubmission(String detailId) throws Exception {
    dtlExamService.deleteDetailExam(detailId);
  }

}
