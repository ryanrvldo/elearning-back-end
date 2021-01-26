package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.TeacherDao;
import com.lawencon.elearning.dto.TeacherProfileDTO;
import com.lawencon.elearning.dto.TeacherResponseDTO;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Experience;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.service.ExperienceService;
import com.lawencon.elearning.service.TeacherService;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Service
public class TeacherServiceImpl extends BaseServiceImpl implements TeacherService {

  @Autowired
  private TeacherDao teacherDao;

  @Autowired
  private ExperienceService experienceService;


  @Override
  public void updateIsActive(String id, String userId) throws Exception {
    try {
      begin();
      teacherDao.updateIsActive(id, userId);
      commit();
    } catch (Exception e) {
      e.printStackTrace();
      rollback();
    }

  }

  @Override
  public List<Teacher> getAllTeachers() throws Exception {
    List<Teacher> listTeachers = teacherDao.getAllTeachers();
    if (listTeachers == null) {
      throw new Exception("Teachers data have not been registered");
    }
    return listTeachers;
  }

  @Override
  public List<Teacher> allTeachersForAdmin() throws Exception {
    List<Teacher> listTeachers = teacherDao.allTeachersForAdmin();
    if (listTeachers == null) {
      throw new Exception("Teachers data have not been registered");
    }
    return listTeachers;
  }

  @Override
  public void saveTeacher(Teacher data) throws Exception {
    if (data.getId() != null) {
      throw new IllegalRequestException("id", data.getId());
    }
    data.setCreatedAt(LocalDateTime.now());
    teacherDao.saveTeacher(data, null);
  }

  @Override
  public Teacher findTeacherById(String id) throws Exception {
    validateNullId(id, "Id");
    return teacherDao.findTeacherById(id);
  }


  @Override
  public TeacherProfileDTO findTeacherByIdCustom(String id) throws Exception {
    validateNullId(id, "Id");

    Teacher teacher = teacherDao.findTeacherByIdCustom(id);
    List<Experience> experiences = experienceService.getAllByTeacherId(id);
    TeacherResponseDTO teacherResponse = new TeacherResponseDTO(teacher.getUser().getFirstName(),
        teacher.getUser().getLastName(), teacher.getUser().getEmail(), teacher.getCreatedAt(),
        teacher.getGender());
    TeacherProfileDTO teacherProfile = new TeacherProfileDTO();
    teacherProfile.setTeacher(teacherResponse);
    teacherProfile.setExperiences(experiences);

    return teacherProfile;
  }

  @Override
  public void updateTeacher(Teacher data) throws Exception {
    validateNullId(data.getId(), "Id");
    teacherDao.updateTeacher(data, null);

  }

  @Override
  public Teacher getFullNameByUserId(String userId) throws Exception {
    validateNullId(userId, "User Id");
    return teacherDao.findByUserId(userId);
  }

  @Override
  public void deleteTeacherById(String id) throws Exception {
    validateNullId(id, "Id");
    commit();
    teacherDao.deleteTeacherById(id);
    begin();
  }

  @Override
  public Teacher findByIdForCourse(String id) throws Exception {
    validateNullId(id, "Id");
    return teacherDao.findByIdForCourse(id);
  }

  private void validateNullId(String id, String msg) throws Exception {
    if (id == null || id.trim().isEmpty()) {
      throw new IllegalRequestException(msg, id);
    }
  }

}
