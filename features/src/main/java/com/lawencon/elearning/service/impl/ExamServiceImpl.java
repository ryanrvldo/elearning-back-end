package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
import com.lawencon.elearning.dto.file.FileCreateRequestDto;
import com.lawencon.elearning.dto.file.FileResponseDto;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.FileType;
import com.lawencon.elearning.model.Module;
import com.lawencon.elearning.service.DetailExamService;
import com.lawencon.elearning.service.ExamService;
import com.lawencon.elearning.service.FileService;
import com.lawencon.elearning.util.MailUtils;
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
  private ValidationUtil validateUtil;

  @Autowired
  private DetailExamService dtlExamService;

  @Autowired
  private MailUtils mailUtils;

  @Override
  public List<Exam> getAllExams() throws Exception {
    return Optional.ofNullable(examDao.getAllExams()).orElseThrow(
        () -> new DataIsNotExistsException("Exam is empty and has not been initialized."));
  }

  @Override
  public void saveExam(MultipartFile multiPartFile, String body) throws Exception {

    if (body == null && multiPartFile == null) {
      throw new IllegalRequestException("Teacher Exam data cannot be empty!");
    }
    ObjectMapper obj = new ObjectMapper();
    obj.registerModule(new JavaTimeModule());

    TeacherExamRequestDTO teacherExam;
    try {
      teacherExam = obj.readValue(body, TeacherExamRequestDTO.class);
    } catch (Exception e) {
      throw new IllegalRequestException("Invalid create exam request.");
    }

    validateUtil.validate(teacherExam);
    validateUtil.validateUUID(teacherExam.getModuleId());
    if (teacherExam.getStartTime().compareTo(teacherExam.getEndTime()) > 0) {
      throw new IllegalRequestException("End time cannot be greather than start Time");
    }

    FileCreateRequestDto contentString = new FileCreateRequestDto();
    contentString.setType(FileType.EXAM.toString());
    contentString.setUserId(teacherExam.getCreatedBy());

    String content = obj.writeValueAsString(contentString);

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
          Optional.ofNullable(fileService.createFile(multiPartFile, content))
              .orElseThrow(() -> new DataIsNotExistsException("Wrong file submission"));
      file.setId(fileResponseDTO.getId());
      examDao.saveExam(exam, null);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }

    // SENGAJA DI COMMENT!
    // String[] emailTo = {"ryanrumapea@gmail.com", "muhammadapry14@gmail.com",
    // "williamgolden54@gmail.com", "galihdikapermana98@gmail.com",
    // "farrelyudapraditya96@gmail.com", "dzakyfadhl@gmail.com"};

    String[] emailTo = {"lawerning.acc@gmail.com"};
    EmailSetupDTO email = new EmailSetupDTO();
    email.setTo(emailTo);
    email.setSubject("TEST SMTP");
    email.setBody("ORANG ORANG TOLOL");
    new EmailServiceImpl(mailUtils, email).start();
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
    return Optional.ofNullable(examDao.findExamById(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  @Override
  public List<ExamsModuleResponseDTO> getExamsByModule(String moduleId) throws Exception {
    validateNullId(moduleId, "Id Module");
    validateUtil.validateUUID(moduleId);
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
  public List<ScoreAverageResponseDTO> getListScoreAvg(String studentId) throws Exception {
    validateNullId(studentId, "Student Id");
    return dtlExamService.getListScoreAvg(studentId);
  }

  @Override
  public List<SubmissionsByExamResponseDTO> getExamSubmissions(String examId) throws Exception {
    validateNullId(examId, "Exam Id");
    return dtlExamService.getExamSubmission(examId);
  }

  @Override
  public List<SubmissionStudentResponseDTO> getStudentExamSubmission(String examId,
      String studentId) throws Exception {
    validateNullId(examId, "exam id");
    validateNullId(studentId, "student id");
    return dtlExamService.getStudentExamSubmission(examId, studentId);
  }

  @Override
  public void submitAssignemt(MultipartFile multiPartFile, String examId, String studentId)
      throws Exception {
    if (examId == null && studentId == null) {
      throw new IllegalRequestException("Student Exam data cannot be empty!");
    }
    dtlExamService.sendStudentExam(multiPartFile, examId, studentId);
  }

  @Override
  public void updateScoreAssignment(UpdateScoreRequestDTO data) throws Exception {
    validateNullId("id", data.getId());
    if (data.getGrade() == null || data.getGrade() < 0) {
      throw new IllegalRequestException("Bad request with score! cannot be empty or less than 0");
    }
    validateNullId("Teacher Id", data.getUpdatedBy());

    dtlExamService.updateScoreStudent(data);
  }

  @Override
  public List<ScoreReportDTO> getListScoreReport(String id) throws Exception {
    validateNullId(id, "id");

    return Optional.ofNullable(dtlExamService.getListScoreReport(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));
  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }



}
