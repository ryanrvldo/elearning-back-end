package com.lawencon.elearning.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.DetailExamDao;
import com.lawencon.elearning.dto.FileResponseDto;
import com.lawencon.elearning.dto.exam.detail.ScoreAverageResponseDTO;
import com.lawencon.elearning.dto.exam.detail.SubmissionsByExamResponseDTO;
import com.lawencon.elearning.dto.student.StudentExamDTO;
import com.lawencon.elearning.error.DataIsNotExistsException;
import com.lawencon.elearning.model.DetailExam;
import com.lawencon.elearning.model.Exam;
import com.lawencon.elearning.model.File;
import com.lawencon.elearning.model.Student;
import com.lawencon.elearning.service.DetailExamService;
import com.lawencon.elearning.service.FileService;
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

  @Override
  public List<ScoreAverageResponseDTO> getListScoreAvg(String id) throws Exception {
    List<DetailExam> listDetail = dtlExamDao.getListScoreAvg(id);
    if (listDetail == null) {
      throw new DataIsNotExistsException("Data is not exist");
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
  public List<DetailExam> getListScoreReport(String id) throws Exception {
    return dtlExamDao.getListScoreReport(id);
  }

  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    begin();
    dtlExamDao.updateIsActive(id, userId);
    commit();
  }

  @Override
  public void insertDetailExam(DetailExam dtlExam, Callback before) throws Exception {
    dtlExamDao.insertDetailExam(dtlExam, null);
  }

  @Override
  public void updateDetailExam(DetailExam dtlExam, Callback before) throws Exception {
    setupUpdatedValue(dtlExam, () -> dtlExamDao.getDetailById(dtlExam.getId()));
    dtlExamDao.updateDetailExam(dtlExam, null);
  }

  @Override
  public void deleteDetailExam(String id) throws Exception {
    begin();
    dtlExamDao.deleteDetailExam(id);
    commit();
  }

  @Override
  public void updateScoreStudent(String id, Double score, String userId) throws Exception {
    begin();
    dtlExamDao.updateScoreStudent(id, score, userId);
    commit();
  }

  @Override
  public List<SubmissionsByExamResponseDTO> getExamSubmission(String id) throws Exception {
    List<DetailExam> detailExams = Optional.ofNullable(dtlExamDao.getExamSubmission(id))
        .orElseThrow(() -> new DataIsNotExistsException("id", id));

    List<SubmissionsByExamResponseDTO> submissionByExamsDTO = new ArrayList<SubmissionsByExamResponseDTO>();

    detailExams.forEach(val->{
      SubmissionsByExamResponseDTO submission = new SubmissionsByExamResponseDTO();
      submission.setId(val.getId());
      submission.setCode(val.getTrxNumber());
      submission.setFirstName(val.getStudent().getUser().getFirstName());
      submission.setFirstName(val.getStudent().getUser().getLastName());
      submission.setGrade(val.getGrade());
      submission.setSubmittedDate(val.getTrxDate());

      submissionByExamsDTO.add(submission);
    });

    return submissionByExamsDTO;

  }

  @Override
  public void sendStudentExam(MultipartFile multiPartFile, String content, String body)
      throws Exception {
    FileResponseDto fileResponseDTO =
        Optional.ofNullable(fileService.createFile(multiPartFile, content))
            .orElseThrow(() -> new DataIsNotExistsException("Id file not found!"));
    StudentExamDTO studentExamDTO = new ObjectMapper().readValue(body, StudentExamDTO.class);
    validationUtil.validate(studentExamDTO);

    Exam exam = new Exam();
    exam.setId(studentExamDTO.getExamId());
    exam.setVersion(studentExamDTO.getExamVersion());

    Student student = new Student();
    student.setId(studentExamDTO.getStudentId());
    student.setVersion(studentExamDTO.getStudentVersion());

    File file = new File();
    file.setId(fileResponseDTO.getId());

    DetailExam detailExam = new DetailExam();
    detailExam.setCreatedBy(studentExamDTO.getCreatedBy());
    detailExam.setCreatedAt(LocalDateTime.now());
    detailExam.setTrxDate(LocalDate.now());
    detailExam.setTrxNumber(TransactionNumberUtils.generateDtlExamTrxNumber());
    detailExam.setGrade(0D);
    detailExam.setStudent(student);
    detailExam.setExam(exam);
    detailExam.setFile(file);

    dtlExamDao.sendStudentExam(detailExam);
  }



}
