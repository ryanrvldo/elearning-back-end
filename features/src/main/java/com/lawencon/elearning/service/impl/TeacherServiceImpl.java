package com.lawencon.elearning.service.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lawencon.base.BaseServiceImpl;
import com.lawencon.elearning.dao.TeacherDao;
import com.lawencon.elearning.dto.DeleteRequestDTO;
import com.lawencon.elearning.dto.TeacherForAdminDTO;
import com.lawencon.elearning.dto.TeacherProfileDTO;
import com.lawencon.elearning.dto.TeacherRequestDTO;
import com.lawencon.elearning.dto.TeacherResponseDTO;
import com.lawencon.elearning.dto.UpdateTeacherRequestDTO;
import com.lawencon.elearning.error.IllegalRequestException;
import com.lawencon.elearning.model.Experience;
import com.lawencon.elearning.model.Role;
import com.lawencon.elearning.model.Teacher;
import com.lawencon.elearning.model.User;
import com.lawencon.elearning.service.ExperienceService;
import com.lawencon.elearning.service.TeacherService;
import com.lawencon.elearning.service.UserService;

/**
 *  @author Dzaky Fadhilla Guci
 */

@Service
public class TeacherServiceImpl extends BaseServiceImpl implements TeacherService {

  @Autowired
  private TeacherDao teacherDao;

  @Autowired
  private ExperienceService experienceService;

  @Autowired
  private UserService userService;
  

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
  public List<TeacherForAdminDTO> allTeachersForAdmin() throws Exception {
    List<Teacher> listTeachers = teacherDao.allTeachersForAdmin();
    if (listTeachers == null) {
      throw new Exception("Teachers data have not been registered");
    }
    
    List<TeacherForAdminDTO> listResult = new ArrayList<>();
    listTeachers.forEach(val->{
      TeacherForAdminDTO teacherAdminDTO = new TeacherForAdminDTO();
      teacherAdminDTO.setId(val.getId());
      teacherAdminDTO.setActive(val.getIsActive());
      teacherAdminDTO.setCode(val.getCode());
      teacherAdminDTO.setPhone(val.getPhone());
      teacherAdminDTO.setGender(val.getGender());
      teacherAdminDTO.setUsername(val.getUser().getUsername());
      teacherAdminDTO.setVersion(val.getVersion());
      listResult.add(teacherAdminDTO);
    });

    return listResult;
  }

  @Override
  public void saveTeacher(TeacherRequestDTO data) throws Exception {
    User user = new User();
    user.setFirstName(data.getFirstName());
    user.setLastName(data.getLastName());
    user.setUsername(data.getUsername());
    user.setPassword(data.getPassword());
    user.setEmail(data.getEmail());
    Role role = new Role();
    role.setId(data.getRoleId());
    role.setVersion(data.getRoleVersion());
    user.setRole(role);
    userService.addUser(user);

    Teacher teacher = new Teacher();
    teacher.setUser(user);
    teacher.setCode(data.getCode());
    teacher.setPhone(data.getPhone());
    teacher.setGender(data.getGender());
    teacher.setTitleDegree(data.getTitleDegree());
    teacher.setCreatedAt(LocalDateTime.now());
    teacher.setCreatedBy(data.getCreatedBy());
    teacherDao.saveTeacher(teacher, null);
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
  public void updateTeacher(UpdateTeacherRequestDTO data) throws Exception {
    validateNullId(data.getId(), "Id");
    Teacher teacherDB = findTeacherById(data.getId());
    teacherDB.setTitleDegree(data.getTitleDegree());
    teacherDB.setGender(data.getGender());
    teacherDB.setUpdatedBy(data.getUpdatedBy());

    User user = new User();
    user.setFirstName(data.getFirstName());
    user.setId(teacherDB.getUser().getId());
    user.setLastName(data.getLastName());
    user.setEmail(data.getEmail());
    userService.updateUser(user);

    teacherDao.updateTeacher(teacherDB, null);

  }

  @Override
  public Teacher getFullNameByUserId(String userId) throws Exception {
    validateNullId(userId, "User Id");
    return teacherDao.findByUserId(userId);
  }

  @Override
  public void deleteTeacherById(DeleteRequestDTO deleteReq) throws Exception {
    validateNullId(deleteReq.getId(), "Id");
    if (teacherDao.checkConstraint(deleteReq.getId()) == 0) {
      teacherDao.deleteTeacherById(deleteReq.getId());
    } else {
      updateIsActive(deleteReq.getId(), deleteReq.getUpdatedBy());
    }
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
