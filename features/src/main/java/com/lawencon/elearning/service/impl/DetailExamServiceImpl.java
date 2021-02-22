package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.DetailExamDao;
import com.lawencon.elearning.dto.exam.UpdateScoreRequestDTO;
import com.lawencon.elearning.dto.exam.detail.ScoreAverageResponseDTO;
import com.lawencon.elearning.dto.exam.detail.ScoreReportDTO;
import com.lawencon.elearning.dto.exam.detail.SubmissionStudentResponseDTO;
import com.lawencon.elearning.dto.exam.detail.SubmissionsByExamResponseDTO;
import com.lawencon.elearning.dto.file.FileRequestDto;
import com.lawencon.elearning.dto.file.FileResponseDto;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.FileType;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.service.DetailExamService;
import com.lawencon.elearning.service.ExamService;
import com.lawencon.elearning.service.FileService;
import com.lawencon.elearning.service.StudentService;
import com.lawencon.elearning.util.TransactionNumberUtils;
import com.lawencon.elearning.util.ValidationUtil;
import com.lawencon.util.Callback;

/**
 * @author : Galih Dika Permana
 */
@Service
public class DetailExamServiceImpl extends BaseServiceImpl implements DetailExamService {

  @Autowired
  private DetailExamDao dtlExamDao;

  @Autowired
  private FileService fileService;

  @Autowired
  private ValidationUtil validationUtil;

  @Autowired
  private ExamService examService;

  @Autowired
  private StudentService studentService;

  @Override
  public List<ScoreAverageResponseDTO> getListScoreAvg(String id) throws Exception {
    validationUtil.validateUUID(id);
    List<DetailExam> listDetail = dtlExamDao.getListScoreAvg(id);
    if (listDetail == null) {
      return Collections.emptyList();
    }
    List<ScoreAverageResponseDTO> scoreAveragesDTO = new ArrayList<>();
    listDetail.forEach(val -> {
      ScoreAverageResponseDTO scoreAvg = new ScoreAverageResponseDTO();
      scoreAvg.setAverageScore(val.getGrade());
      scoreAvg.setCode((val.getExam().getModule().getCode()));
      scoreAvg.setTitle(val.getExam().getModule().getTitle());
      scoreAvg.setStartTime(val.getExam().getStartTime());
      scoreAvg.setEndTime(val.getExam().getEndTime());
      scoreAveragesDTO.add(scoreAvg);
    });
    return scoreAveragesDTO;
  }

  @Override
  public List<ScoreReportDTO> getListScoreReport(String id) throws Exception {
    validationUtil.validateUUID(id);
    List<DetailExam> detailExams = dtlExamDao.getListScoreReport(id);
    if (detailExams == null) {
      return Collections.emptyList();
    }

    List<ScoreReportDTO> scoreReports = new ArrayList<>();
    for (DetailExam detail : detailExams) {
      ScoreReportDTO dataScore = new ScoreReportDTO();
      dataScore.setCourseCode(detail.getExam().getModule().getCourse().getCode());
      dataScore.setCourseDescription(detail.getExam().getModule().getCourse().getDescription());
      dataScore.setModuleCode(detail.getExam().getModule().getCode());
      dataScore.setModuleTitle(detail.getExam().getModule().getDescription());
      dataScore.setExamStart(detail.getExam().getStartTime());
      dataScore.setExamEnd(detail.getExam().getEndTime());
      dataScore.setGrade(detail.getGrade());
      scoreReports.add(dataScore);
    }
    return scoreReports;
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    validationUtil.validateUUID(id);
    begin();
    dtlExamDao.updateIsActive(id, userId);
    commit();
  }

  @Override
  public void insertDetailExam(DetailExam dtlExam, Callback before) throws Exception {
    validationUtil.validate(dtlExam);
    dtlExamDao.insertDetailExam(dtlExam, null);
  }

  @Override
  public void updateDetailExam(DetailExam dtlExam, Callback before) throws Exception {
    validationUtil.validate(dtlExam);
    setupUpdatedValue(dtlExam, () -> dtlExamDao.getDetailById(dtlExam.getId()));
    dtlExamDao.updateDetailExam(dtlExam, null);
  }

  @Override
  public void deleteDetailExam(String id) throws Exception {
    validationUtil.validateUUID(id);
    DetailExam detailExam = dtlExamDao.getDetailById(id);
    if (detailExam == null) {
      throw new DataIsNotExistsException(id);
    }
    if (detailExam.getVersion().equals(0L)
        && LocalDateTime.now().isBefore(detailExam.getExam().getEndTime())) {
      begin();
      dtlExamDao.deleteDetailExam(id);
      commit();
      fileService.deleteFile(detailExam.getFile().getId());
    } else {
      throw new IllegalRequestException("Submission have been graded or deadline time is over.");
    }
  }

  @Override
  public void updateScoreStudent(UpdateScoreRequestDTO data) throws Exception {
    validationUtil.validate(data);
    DetailExam detailExam = Optional.ofNullable(dtlExamDao.getDetailById(data.getId()))
        .orElseThrow(() -> new DataIsNotExistsException("submission id", data.getId()));
    detailExam.setUpdatedBy(data.getUpdatedBy());
    detailExam.setGrade(data.getGrade());
    dtlExamDao.updateScoreStudent(detailExam, null);
  }

  @Override
  public List<SubmissionsByExamResponseDTO> getExamSubmission(String id) throws Exception {
    validationUtil.validateUUID(id);
    return Optional.ofNullable(dtlExamDao.getExamSubmission(id))
        .orElse(Collections.emptyList());
  }

  @Override
  public SubmissionStudentResponseDTO getStudentExamSubmission(String examId,
      String studentId) throws Exception {
    validationUtil.validateUUID(studentId);
    validationUtil.validateUUID(examId);
    SubmissionStudentResponseDTO result =
        dtlExamDao.getStudentExamSubmission(examId, studentId);
    return result;
  }

  @Override
  public void sendStudentExam(MultipartFile multiPartFile, String examId, String studentId)
      throws Exception {
    FileRequestDto fileRequest = new FileRequestDto();
    fileRequest.setType(FileType.ASSIGNMENT.name());
    fileRequest.setUserId(studentId);

    validationUtil.validateUUID(examId);
    validationUtil.validateUUID(studentId);

    LocalDateTime timeNow = LocalDateTime.now();
    Exam exam = examService.findExamById(examId);
    if (exam.getStartTime().isAfter(timeNow) || exam.getEndTime().isBefore(timeNow)) {
      throw new IllegalRequestException("Cannot send submission at this time");
    }

    Student student = studentService.getStudentById(studentId);

    File file = new File();

    DetailExam detailExam = new DetailExam();
    detailExam.setCreatedBy(studentId);
    detailExam.setCreatedAt(LocalDateTime.now());
    detailExam.setTrxDate(LocalDate.now());
    detailExam.setTrxNumber(TransactionNumberUtils.generateDtlExamTrxNumber());
    detailExam.setGrade(0D);
    detailExam.setStudent(student);
    detailExam.setExam(exam);
    detailExam.setFile(file);
    
    try {
      begin();
      FileResponseDto fileResponseDTO =
          Optional.ofNullable(fileService.createFile(multiPartFile, fileRequest))
              .orElseThrow(() -> new DataIsNotExistsException("Id file not found!"));
      file.setId(fileResponseDTO.getId());
      dtlExamDao.sendStudentExam(detailExam);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
      throw e;
    }
  }

  @Override
  public List<DetailExam> getStudentExamReport(String studentId) throws Exception {
    return dtlExamDao.getStudentExamReport(studentId);
  }

  @Override
  public Integer getTotalAssignmentStudent(String moduleId, String studentId) throws Exception {
    return dtlExamDao.getTotalAssignmentStudent(moduleId, studentId);
  }

  @Override
  public Double getAvgScoreAssignmentStudent(String moduleId, String studentId) throws Exception {
    return dtlExamDao.getAvgScoreAssignmentStudent(moduleId, studentId);
  }

}
